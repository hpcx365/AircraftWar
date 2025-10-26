package pers.hpcx.app;

import lombok.Getter;
import lombok.Setter;
import pers.hpcx.aircraft.*;
import pers.hpcx.audio.AudioController;
import pers.hpcx.audio.AudioPlayer;
import pers.hpcx.audio.BufferedAudio;
import pers.hpcx.basic.AbstractFlyingObject;
import pers.hpcx.buff.BaseBuff;
import pers.hpcx.buff.DefaultBulletBuff;
import pers.hpcx.bullet.BaseBullet;
import pers.hpcx.prop.BaseProp;
import pers.hpcx.template.Difficulty;
import pers.hpcx.template.GameTemplate;
import pers.hpcx.trigger.RandomTimedTrigger;
import pers.hpcx.trigger.ScoreTrigger;
import pers.hpcx.trigger.TimedTrigger;
import pers.hpcx.visual.NineGrid;
import pers.hpcx.visual.PaintUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static pers.hpcx.app.Main.WINDOW_HEIGHT;
import static pers.hpcx.app.Main.WINDOW_WIDTH;

@Getter public class GamePanel extends JPanel implements ActionListener {
    
    private static final int REPAINT_INTERVAL = 10;
    private final Timer repaintTimer = new Timer(REPAINT_INTERVAL, this);
    
    private long startTime;
    private long currentTime;
    private long playTime;
    private int score;
    private boolean gameOver;
    private boolean silentMode;
    private int backGroundTop;
    
    private Difficulty difficulty;
    private GameTemplate template;
    private final Random random = new Random();
    
    private double currentEnemySpawnFrequencyRate;
    private double currentEnemyMaxHealthRate;
    private double currentEnemyBulletPowerRate;
    private double currentEnemyMaxSpeedRate;
    private double currentEnemyFireFrequencyRate;
    private double currentEnemyBulletSpeedRate;
    
    private final TimedTrigger rateUpdateTrigger = new TimedTrigger();
    private final RandomTimedTrigger mobEnemySpawnTrigger = new RandomTimedTrigger(100);
    private final RandomTimedTrigger eliteEnemySpawnTrigger = new RandomTimedTrigger(100);
    private final RandomTimedTrigger superEliteEnemySpawnTrigger = new RandomTimedTrigger(100);
    private final ScoreTrigger bossEnemySpawnTrigger = new ScoreTrigger();
    
    private final HeroController heroController = new HeroController();
    
    private HeroAircraft heroAircraft;
    private final List<BaseBullet> heroBullets = new ArrayList<>();
    private final List<BaseBullet> enemyBullets = new ArrayList<>();
    private final List<AbstractAircraft> enemyAircrafts = new ArrayList<>();
    private final List<BaseProp> props = new ArrayList<>();
    
    private AudioPlayer audioPlayer;
    private AudioController bgmController;
    private AudioController bossBgmController;
    private final List<AudioController> audioControllers = new ArrayList<>();
    
    @Setter private Runnable gameOverAction;
    
    /**
     * 游戏启动入口
     */
    public void start(StartPanel startPanel) {
        // 初始化
        currentTime = startTime = System.currentTimeMillis();
        playTime = 0;
        score = 0;
        gameOver = false;
        backGroundTop = 0;
        
        difficulty = startPanel.getDifficulty();
        silentMode = startPanel.getSilentModeCheckBox().isSelected();
        template = difficulty.newTemplate();
        
        currentEnemySpawnFrequencyRate = 1;
        currentEnemyMaxHealthRate = 1;
        currentEnemyBulletPowerRate = 1;
        currentEnemyMaxSpeedRate = 1;
        currentEnemyFireFrequencyRate = 1;
        currentEnemyBulletSpeedRate = 1;
        
        rateUpdateTrigger.reset(template.rateUpdateInterval());
        mobEnemySpawnTrigger.reset(template.initialEnemySpawnInterval(MobEnemy.class));
        eliteEnemySpawnTrigger.reset(template.initialEnemySpawnInterval(EliteEnemy.class));
        superEliteEnemySpawnTrigger.reset(template.initialEnemySpawnInterval(SuperEliteEnemy.class));
        bossEnemySpawnTrigger.reset(template.bossSpawnScoreGap());
        
        heroBullets.clear();
        enemyBullets.clear();
        enemyAircrafts.clear();
        props.clear();
        
        heroAircraft = new HeroAircraft(template.heroMaxHealth());
        heroAircraft.setLocationX(Main.WINDOW_WIDTH * 0.5);
        heroAircraft.setLocationY(Main.WINDOW_HEIGHT - heroAircraft.getImage().getHeight());
        heroAircraft.setFireTrigger(new TimedTrigger(template.heroFireInterval()));
        heroAircraft.getBuffs().add(new DefaultBulletBuff());
        
        setFocusable(true);
        heroController.install(this);
        repaintTimer.start();
        play(ResourceManager.BACKGROUND_AUDIO);
    }
    
    /**
     * 定时任务：绘制、对象产生、碰撞判定、击毁、道具生效及结束判定
     */
    @Override public void actionPerformed(ActionEvent e) {
        // 计时
        currentTime = System.currentTimeMillis();
        playTime = currentTime - startTime;
        
        // 倍率更新
        updateRates();
        
        // buff 更新
        updateBuffs();
        
        // 新敌机产生
        spawnEnemy();
        
        // 飞机射出子弹
        shoot();
        
        // 英雄机、敌机、子弹移动
        moveObjects();
        
        // 撞击检测
        checkCrash();
        
        // 道具生效
        propTakesEffect();
        
        // 后处理
        removeInvalidObjects();
        removeStoppedAudio();
        
        // 检查英雄机是否存活
        if (!heroAircraft.isAlive()) {
            // 游戏结束
            onGameOver();
        }
        
        //每个时刻重绘界面
        repaint();
    }
    
    //***********************
    //      Action 各部分
    //***********************
    
    private void updateRates() {
        if (!rateUpdateTrigger.isTriggered(playTime)) {
            return;
        }
        
        currentEnemySpawnFrequencyRate += template.enemySpawnFrequencyGrowthRate();
        currentEnemyMaxHealthRate += template.enemyMaxHealthGrowthRate();
        currentEnemyBulletPowerRate += template.enemyBulletPowerGrowthRate();
        currentEnemyMaxSpeedRate += template.enemyMaxSpeedGrowthRate();
        currentEnemyFireFrequencyRate += template.enemyFireFrequencyGrowthRate();
        currentEnemyBulletSpeedRate += template.enemyBulletSpeedGrowthRate();
        
        System.out.printf(
            "游戏难度已更新！生成速率：%.02f，敌机血量倍率：%.02f，敌机子弹伤害倍率：%.02f，敌机最大速度倍率：%.02f，敌机射击频率倍率：%.02f，敌机子弹速度倍率：%.02f%n",
            currentEnemySpawnFrequencyRate,
            currentEnemyMaxHealthRate,
            currentEnemyBulletPowerRate,
            currentEnemyMaxSpeedRate,
            currentEnemyFireFrequencyRate,
            currentEnemyBulletSpeedRate
        );
    }
    
    public int currentEnemySpawnDuration(Class<? extends AbstractAircraft> enemyType) {
        return (int) Math.round(template.initialEnemySpawnInterval(enemyType) / currentEnemySpawnFrequencyRate);
    }
    
    public int currentEnemyMaxHealth(Class<? extends AbstractAircraft> enemyType) {
        return (int) Math.round(template.initialEnemyMaxHealth(enemyType) * currentEnemyMaxHealthRate);
    }
    
    public int currentEnemyBulletPower(Class<? extends AbstractAircraft> enemyType) {
        return (int) Math.round(template.initialEnemyBulletPower(enemyType) * currentEnemyBulletPowerRate);
    }
    
    public double currentEnemyMaxSpeed(Class<? extends AbstractAircraft> enemyType) {
        return template.initialEnemyMaxSpeed(enemyType) * currentEnemyMaxSpeedRate;
    }
    
    public int currentEnemyFireDuration(Class<? extends AbstractAircraft> enemyType) {
        return (int) Math.round(template.initialEnemyFireInterval(enemyType) / currentEnemyFireFrequencyRate);
    }
    
    public double currentEnemyBulletSpeed(Class<? extends AbstractAircraft> enemyType) {
        return template.initialEnemyBulletSpeed(enemyType) * currentEnemyBulletSpeedRate;
    }
    
    /**
     * BUFF 更新
     */
    private void updateBuffs() {
        heroAircraft.getBuffs().forEach(buff -> buff.update(REPAINT_INTERVAL, this));
        heroAircraft.getBuffs().removeIf(Predicate.not(BaseBuff::isValid));
    }
    
    /**
     * 生成敌机
     */
    private void spawnEnemy() {
        Consumer<AbstractAircraft> enemyInitializer = enemy -> {
            Class<? extends AbstractAircraft> enemyType = enemy.getClass();
            enemy.setSpeedX(0);
            enemy.setSpeedY(random.nextDouble(0.5, 1) * currentEnemyMaxSpeed(enemyType));
            enemy.setLocationX(random.nextDouble(enemy.getImage().getWidth() * 0.5, Main.WINDOW_WIDTH - enemy.getImage().getHeight() * 0.5));
            enemy.setLocationY(-enemy.getImage().getHeight() * 0.5);
            enemy.setFireTrigger(new TimedTrigger(currentEnemyFireDuration(enemyType)));
            enemy.setFireStrategy(template.enemyFireStrategy(enemyType, currentEnemyBulletPower(enemyType), currentEnemyBulletSpeed(enemyType)));
        };
        
        if (mobEnemySpawnTrigger.isTriggered(playTime, random)) {
            MobEnemy enemy = new MobEnemy(currentEnemyMaxHealth(MobEnemy.class));
            enemyInitializer.accept(enemy);
            enemyAircrafts.add(enemy);
        }
        
        if (eliteEnemySpawnTrigger.isTriggered(playTime, random)) {
            EliteEnemy enemy = new EliteEnemy(currentEnemyMaxHealth(EliteEnemy.class));
            enemyInitializer.accept(enemy);
            enemyAircrafts.add(enemy);
        }
        
        if (superEliteEnemySpawnTrigger.isTriggered(playTime, random)) {
            SuperEliteEnemy enemy = new SuperEliteEnemy(currentEnemyMaxHealth(SuperEliteEnemy.class));
            enemyInitializer.accept(enemy);
            double theta = Math.toRadians(random.nextDouble(60, 120));
            enemy.setSpeedX(Math.cos(theta) * enemy.getSpeedY());
            enemy.setSpeedY(Math.sin(theta) * enemy.getSpeedY());
            enemyAircrafts.add(enemy);
        }
        
        // 场上不会出现两架 Boss
        if (bossEnemySpawnTrigger.isTriggered(score) && enemyAircrafts.stream().noneMatch(enemy -> enemy instanceof BossEnemy)) {
            BossEnemy enemy = new BossEnemy(currentEnemyMaxHealth(BossEnemy.class));
            enemyInitializer.accept(enemy);
            enemy.setLocationX(Main.WINDOW_WIDTH * 0.5);
            enemyAircrafts.add(enemy);
            play(ResourceManager.BOSS_BACKGROUND_AUDIO);
        }
    }
    
    /**
     * 1. 英雄射击<br>
     * 2. 敌机射击<br>
     */
    private void shoot() {
        if (heroAircraft.getFireTrigger().isTriggered(playTime)) {
            heroBullets.addAll(heroAircraft.shoot());
            play(ResourceManager.BULLET_AUDIO);
        }
        
        enemyAircrafts.stream()
            .filter(AbstractFlyingObject::isAlive)
            .filter(enemyAircraft -> enemyAircraft.getFireTrigger() != null)
            .filter(enemyAircraft -> enemyAircraft.getFireTrigger().isTriggered(playTime))
            .forEach(enemyAircraft -> enemyBullets.addAll(enemyAircraft.shoot()));
    }
    
    /**
     * 1. 英雄子弹移动<br>
     * 2. 敌机子弹移动<br>
     * 3. 敌机移动<br>
     * 4. 道具移动<br>
     */
    private void moveObjects() {
        heroController.move();
        heroBullets.forEach(AbstractFlyingObject::forward);
        enemyBullets.forEach(AbstractFlyingObject::forward);
        enemyAircrafts.forEach(AbstractFlyingObject::forward);
        props.forEach(AbstractFlyingObject::forward);
    }
    
    /**
     * 1. 敌机攻击英雄<br>
     * 2. 英雄攻击/撞击敌机<br>
     * 3. 结算奖励<br>
     */
    private void checkCrash() {
        // 敌机子弹攻击英雄
        enemyBullets.stream()
            .filter(AbstractFlyingObject::isAlive)
            .filter(heroAircraft::crash)
            .forEach(bullet -> {
                // 英雄机撞击到敌机子弹损失一定生命值
                bullet.setAlive(false);
                heroAircraft.decreaseHp(bullet.getPower());
            });
        
        // 英雄子弹攻击敌机
        heroBullets.stream()
            .filter(AbstractFlyingObject::isAlive)
            .forEach(bullet -> enemyAircrafts.stream()
                .filter(AbstractFlyingObject::isAlive)
                .filter(bullet::crash)
                .forEach(enemy -> {
                    // 敌机撞击到英雄机子弹损失一定生命值
                    bullet.setAlive(false);
                    enemy.decreaseHp(bullet.getPower());
                })
            );
        
        // 英雄机与敌机相撞
        enemyAircrafts.stream()
            .filter(AbstractFlyingObject::isAlive)
            .filter(heroAircraft::crash)
            .forEach(enemy -> {
                // 英雄机撞击到敌机损失一定生命值
                enemy.setAlive(false);
                heroAircraft.decreaseHp(template.crashEnemyDamage(enemy.getClass()));
            });
        
        // 结算摧毁敌机奖励
        enemyAircrafts.stream()
            .filter(enemy -> enemy.getHealth() <= 0)
            .forEach(this::onEnemyDestroy);
    }
    
    /**
     * 根据敌机类型增加分数并生成道具
     */
    private void onEnemyDestroy(AbstractAircraft enemy) {
        Class<? extends AbstractAircraft> enemyType = enemy.getClass();
        score += template.scoreOnEnemyDestroy(enemyType);
        
        int numProps = template.numPropsOnEnemyDestroy(enemyType, random);
        for (int i = 0; i < numProps; i++) {
            BaseProp prop = template.choosePropOnEnemyDestroy(enemyType, random);
            prop.setLocationX(enemy.getLocationX());
            prop.setLocationY(enemy.getLocationY());
            prop.setSpeedX(random.nextDouble(-3, 3));
            prop.setSpeedY(random.nextDouble(1, 3));
            props.add(prop);
        }
        
        if (enemy instanceof BossEnemy) {
            play(ResourceManager.BACKGROUND_AUDIO);
        }
        play(ResourceManager.BULLET_HIT_AUDIO);
    }
    
    /**
     * 道具生效
     */
    private void propTakesEffect() {
        props.stream()
            .filter(AbstractFlyingObject::isAlive)
            .filter(heroAircraft::crash)
            .forEach(prop -> {
                prop.takeEffect(this);
                prop.setAlive(false);
                play(ResourceManager.GET_SUPPLY_AUDIO);
            });
    }
    
    /**
     * 1. 删除无效的子弹<br>
     * 2. 删除无效的敌机<br>
     * 3. 删除无效的道具<br>
     */
    private void removeInvalidObjects() {
        heroBullets.removeIf(AbstractFlyingObject::isInvalid);
        enemyBullets.removeIf(AbstractFlyingObject::isInvalid);
        enemyAircrafts.removeIf(AbstractFlyingObject::isInvalid);
        props.removeIf(AbstractFlyingObject::isInvalid);
    }
    
    /**
     * 游戏结束<br>
     * 1. 停止游戏循环<br>
     * 2. 停止所有音频<br>
     * 3. 停止英雄控制<br>
     */
    private void onGameOver() {
        gameOver = true;
        repaintTimer.stop();
        
        stopAllAudio();
        play(ResourceManager.GAME_OVER_AUDIO);
        
        setFocusable(false);
        heroController.uninstall();
        addMouseListener(new MouseAdapter() {
            
            @Override public void mousePressed(MouseEvent e) {
                if (gameOverAction != null) {
                    gameOverAction.run();
                }
                GamePanel.this.removeMouseListener(this);
            }
        });
    }
    
    //***********************
    //      Audio 各部分
    //***********************
    
    public void play(BufferedAudio audio) {
        if (silentMode) {
            return;
        }
        if (audioPlayer == null) {
            audioPlayer = new AudioPlayer();
        }
        if (audio == ResourceManager.BACKGROUND_AUDIO) {
            bgmController = audioPlayer.playInfinitely(audio);
            bgmController.start();
            if (bossBgmController != null) {
                bossBgmController.stop();
            }
            return;
        }
        if (audio == ResourceManager.BOSS_BACKGROUND_AUDIO) {
            bossBgmController = audioPlayer.playInfinitely(audio);
            bossBgmController.start();
            if (bgmController != null) {
                bgmController.stop();
            }
            return;
        }
        AudioController controller = audioPlayer.playOnce(audio);
        audioControllers.add(controller);
        controller.start();
    }
    
    private void stopAllAudio() {
        if (bgmController != null) {
            bgmController.stop();
        }
        if (bossBgmController != null) {
            bossBgmController.stop();
        }
        audioControllers.forEach(AudioController::stop);
        removeStoppedAudio();
    }
    
    private void removeStoppedAudio() {
        if (bgmController != null && bgmController.isStopped()) {
            bgmController = null;
        }
        if (bossBgmController != null && bossBgmController.isStopped()) {
            bossBgmController = null;
        }
        audioControllers.removeIf(AudioController::isStopped);
    }
    
    //***********************
    //      Paint 各部分
    //***********************
    
    /**
     * 游戏动画
     */
    @Override public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(PaintUtils.RENDERING_HINTS_BEST_QUALITY);
        
        // 绘制背景,图片滚动
        g2d.drawImage(template.background(), 0, backGroundTop - WINDOW_HEIGHT, null);
        g2d.drawImage(template.background(), 0, backGroundTop, null);
        backGroundTop = (backGroundTop + 1) % WINDOW_HEIGHT;
        
        // 子弹显示在飞机的下层
        enemyBullets.forEach(obj -> drawFlyingObject(g2d, obj));
        heroBullets.forEach(obj -> drawFlyingObject(g2d, obj));
        enemyAircrafts.forEach(obj -> drawFlyingObject(g2d, obj));
        props.forEach(obj -> drawFlyingObject(g2d, obj));
        drawFlyingObject(g2d, heroAircraft);
        
        //绘制时间、得分和生命值
        drawUI(g2d);
    }
    
    private void drawFlyingObject(Graphics2D g, AbstractFlyingObject object) {
        BufferedImage image = object.getImage();
        double x = object.getLocationX() - image.getWidth() * 0.5;
        double y = object.getLocationY() - image.getHeight() * 0.5;
        g.translate(x, y);
        g.drawImage(image, 0, 0, null);
        g.translate(-x, -y);
    }
    
    private void drawUI(Graphics2D g) {
        String timeText = "%02d:%02d:%02d".formatted(playTime / 1000 / 60, playTime / 1000 % 60, playTime % 1000 / 10);
        String scoreText = "SCORE: " + score;
        String healthText = "HP: " + heroAircraft.getHealth();
        String difficultyText = difficulty.getDescription();
        
        int x = 10;
        int y = 10;
        
        g.setFont(new Font("SansSerif", Font.BOLD, 23));
        
        drawShadowedText(g, timeText, x, y, 3, 3);
        y += 30;
        drawShadowedText(g, scoreText, x, y, 3, 3);
        y += 30;
        drawShadowedText(g, healthText, x, y, 3, 3);
        
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 23));
        
        y += 30;
        drawShadowedText(g, difficultyText, x, y, 3, 3);
        
        if (gameOver) {
            g.setColor(new Color(45, 35, 35, 200));
            g.fillRect(0, WINDOW_HEIGHT / 3, WINDOW_WIDTH, WINDOW_HEIGHT / 3);
            g.setFont(new Font("SansSerif", Font.BOLD, 60));
            drawShadowedText(g, "GAME OVER", 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, 3, 6);
            g.setFont(new Font("SansSerif", Font.BOLD, 30));
            drawShadowedText(g, "点击任意位置继续", 0, WINDOW_HEIGHT * 2 / 3, WINDOW_WIDTH, WINDOW_HEIGHT / 3, 3, 3);
        }
    }
    
    private static final Color TEXT_COLOR = new Color(255, 237, 204);
    private static final Color SHADOW_COLOR = new Color(65, 51, 51);
    
    private void drawShadowedText(Graphics2D g2d, String text, int x, int y, int sx, int sy) {
        g2d.setColor(SHADOW_COLOR);
        PaintUtils.drawTextPoint(g2d, text, NineGrid.SOUTH_EAST, x - sx, y + sy);
        g2d.setColor(TEXT_COLOR);
        PaintUtils.drawTextPoint(g2d, text, NineGrid.SOUTH_EAST, x, y);
    }
    
    private void drawShadowedText(Graphics2D g2d, String text, int x, int y, int w, int h, int sx, int sy) {
        g2d.setColor(SHADOW_COLOR.darker());
        PaintUtils.drawTextRectangle(g2d, text, NineGrid.CENTER, x - sx, y + sy, w, h);
        g2d.setColor(TEXT_COLOR);
        PaintUtils.drawTextRectangle(g2d, text, NineGrid.CENTER, x, y, w, h);
    }
}
