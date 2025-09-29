package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.aircraft.factory.*;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.factory.BombPropFactory;
import edu.hitsz.prop.factory.BulletPropFactory;
import edu.hitsz.prop.factory.HealthPropFactory;
import edu.hitsz.prop.factory.PropFactory;
import edu.hitsz.util.RandomTimedTrigger;
import edu.hitsz.util.ScoreTrigger;
import lombok.Getter;
import pers.hpcx.util.Random;
import pers.hpcx.visual.NineGrid;
import pers.hpcx.visual.PaintUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
@Getter public class Game extends JPanel implements ActionListener {
    
    // 时间间隔(ms)，控制刷新频率
    private static final int TIME_INTERVAL = 10;
    
    // 定时器，定时循环游戏帧
    private final Timer timer = new Timer(TIME_INTERVAL, this);
    
    // 屏幕中出现的敌机最大数量
    private static final int MAX_ENEMY_NUMBER = 20;
    
    // 背景图滚动位置
    private int backGroundTop = 0;
    
    // 当前游戏时间
    private int time = 0;
    
    // 当前得分
    private int score = 0;
    
    // 游戏是否结束
    private boolean gameOver = false;
    
    // 普通敌机随机定时产生触发器
    private final RandomTimedTrigger modEnemySpawnTrigger = new RandomTimedTrigger(500, 1000);
    
    // 精英敌机随机定时产生触发器
    private final RandomTimedTrigger eliteEnemySpawnTrigger = new RandomTimedTrigger(6000, 10000);
    
    // 超级精英敌机随机定时产生触发器
    private final RandomTimedTrigger superEliteEnemySpawnTrigger = new RandomTimedTrigger(12000, 20000);
    
    // Boss 敌机计分产生触发器
    private final ScoreTrigger bossEnemySpawnTrigger = new ScoreTrigger(1000);
    
    private HeroController heroController;
    
    private final List<BaseBullet> heroBullets = new LinkedList<>();
    private final List<BaseBullet> enemyBullets = new LinkedList<>();
    private final List<AbstractAircraft> enemyAircrafts = new LinkedList<>();
    private final List<BaseProp> props = new LinkedList<>();
    
    /**
     * 游戏启动入口
     */
    public void start() {
        // 启动英雄机事件监听
        heroController = new HeroController(this);
        
        // 启动定时器
        timer.start();
    }
    
    /**
     * 定时任务：绘制、对象产生、碰撞判定、击毁、道具生效及结束判定
     */
    public void actionPerformed(ActionEvent e) {
        // 计时
        time += TIME_INTERVAL;
        
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
        
        // 检查英雄机是否存活
        if (!HeroAircraft.getInstance().isAlive()) {
            // 游戏结束
            gameOver = true;
            timer.stop();
        }
        
        //每个时刻重绘界面
        repaint();
    }
    
    //***********************
    //      Action 各部分
    //***********************
    
    /**
     * 生成敌机
     */
    private void spawnEnemy() {
        if (enemyAircrafts.size() < MAX_ENEMY_NUMBER && modEnemySpawnTrigger.isTriggered(TIME_INTERVAL)) {
            // 使用普通敌机工厂创建敌机
            EnemyFactory factory = new MobEnemyFactory();
            AbstractAircraft enemy = factory.createEnemy();
            enemyAircrafts.add(enemy);
        }
        
        if (enemyAircrafts.size() < MAX_ENEMY_NUMBER && eliteEnemySpawnTrigger.isTriggered(TIME_INTERVAL)) {
            // 使用精英敌机工厂创建敌机
            EnemyFactory factory = new EliteEnemyFactory();
            AbstractAircraft enemy = factory.createEnemy();
            enemyAircrafts.add(enemy);
        }
        
        if (enemyAircrafts.size() < MAX_ENEMY_NUMBER && superEliteEnemySpawnTrigger.isTriggered(TIME_INTERVAL)) {
            // 使用超级精英敌机工厂创建敌机
            EnemyFactory factory = new SuperEliteEnemyFactory();
            AbstractAircraft enemy = factory.createEnemy();
            enemyAircrafts.add(enemy);
        }
        
        if (enemyAircrafts.size() < MAX_ENEMY_NUMBER && bossEnemySpawnTrigger.isTriggered(score)) {
            // 场上不会出现两架 Boss
            if (enemyAircrafts.stream().noneMatch(enemy -> enemy instanceof BossEnemy)) {
                // 使用 Boss 敌机工厂创建敌机
                EnemyFactory factory = new BossEnemyFactory();
                AbstractAircraft enemy = factory.createEnemy();
                enemyAircrafts.add(enemy);
            }
        }
    }
    
    /**
     * 1. 英雄射击<br>
     * 2. 敌机射击<br>
     */
    private void shoot() {
        if (HeroAircraft.getInstance().getShootTrigger().isTriggered(TIME_INTERVAL)) {
            heroBullets.addAll(HeroAircraft.getInstance().shoot());
        }
        
        enemyAircrafts.stream()
                .filter(AbstractFlyingObject::isAlive)
                .filter(enemyAircraft -> enemyAircraft.getShootTrigger() != null)
                .filter(enemyAircraft -> enemyAircraft.getShootTrigger().isTriggered(TIME_INTERVAL))
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
                .filter(HeroAircraft.getInstance()::crash)
                .forEach(bullet -> {
                    // 英雄机撞击到敌机子弹损失一定生命值
                    bullet.vanish();
                    HeroAircraft.getInstance().decreaseHp(bullet.getPower());
                });
        
        // 英雄子弹攻击敌机
        heroBullets.stream()
                .filter(AbstractFlyingObject::isAlive)
                .forEach(bullet -> enemyAircrafts.stream()
                        .filter(AbstractFlyingObject::isAlive)
                        .filter(bullet::crash)
                        .forEach(enemy -> {
                            // 敌机撞击到英雄机子弹损失一定生命值
                            bullet.vanish();
                            enemy.decreaseHp(bullet.getPower());
                        }));
        
        // 英雄机与敌机相撞
        enemyAircrafts.stream()
                .filter(AbstractFlyingObject::isAlive)
                .filter(HeroAircraft.getInstance()::crash)
                .forEach(enemy -> {
                    // 英雄机撞击到敌机损失一定生命值
                    enemy.vanish();
                    HeroAircraft.getInstance().decreaseHp(switch (enemy) {
                        case MobEnemy mobEnemy -> 50;
                        case EliteEnemy eliteEnemy -> 200;
                        case SuperEliteEnemy superEliteEnemy -> 500;
                        default -> HeroAircraft.getInstance().getHealth();
                    });
                });
        
        // 结算摧毁敌机奖励
        enemyAircrafts.stream()
                .filter(Predicate.not(AbstractFlyingObject::isAlive))
                .filter(enemy -> enemy.getHealth() <= 0)
                .forEach(this::onEnemyDestroy);
    }
    
    /**
     * 根据敌机类型增加分数并生成道具
     */
    private void onEnemyDestroy(AbstractAircraft enemy) {
        switch (enemy) {
        
        case MobEnemy mobEnemy -> {
            score += 10;
        }
        
        case EliteEnemy eliteEnemy -> {
            score += 50;
            spawnProp(enemy.getLocationX(), enemy.getLocationY());
        }
        
        case SuperEliteEnemy superEliteEnemy -> {
            score += 100;
            spawnProp(enemy.getLocationX(), enemy.getLocationY());
        }
        
        case BossEnemy bossEnemy -> {
            score += 500;
            int numProps = Random.getInstance().nextRange(1, 3);
            for (int i = 0; i < numProps; i++) {
                spawnProp(enemy.getLocationX(), enemy.getLocationY());
            }
        }
        
        default -> {
        }
        }
    }
    
    /**
     * 生成道具
     */
    private void spawnProp(int locationX, int locationY) {
        PropFactory propFactory;
        
        // 随机选择道具工厂
        double rnd = Random.getInstance().nextDouble();
        if (rnd < 0.30) {
            propFactory = new HealthPropFactory();
        } else if (rnd < 0.60) {
            propFactory = new BulletPropFactory();
        } else if (rnd < 0.90) {
            propFactory = new BombPropFactory();
        } else {
            return;
        }
        
        // 使用工厂创建道具
        BaseProp prop = propFactory.createProp();
        prop.setLocationX(locationX);
        prop.setLocationY(locationY);
        prop.setSpeedX(Random.getInstance().nextRange(-3, 3));
        prop.setSpeedY(Random.getInstance().nextRange(1, 3));
        props.add(prop);
    }
    
    /**
     * 道具生效
     */
    private void propTakesEffect() {
        props.stream()
                .filter(AbstractFlyingObject::isAlive)
                .filter(HeroAircraft.getInstance()::crash)
                .forEach(prop -> {
                    prop.takeEffect(HeroAircraft.getInstance());
                    prop.vanish();
                });
    }
    
    /**
     * 1. 删除无效的子弹<br>
     * 2. 删除无效的敌机<br>
     * 3. 删除无效的道具<br>
     */
    private void removeInvalidObjects() {
        heroBullets.removeIf(Predicate.not(AbstractFlyingObject::isAlive));
        enemyBullets.removeIf(Predicate.not(AbstractFlyingObject::isAlive));
        enemyAircrafts.removeIf(Predicate.not(AbstractFlyingObject::isAlive));
        props.removeIf(Predicate.not(AbstractFlyingObject::isAlive));
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
        g2d.drawImage(ImageManager.BACKGROUND_IMAGE, 0, backGroundTop - Main.WINDOW_HEIGHT, null);
        g2d.drawImage(ImageManager.BACKGROUND_IMAGE, 0, backGroundTop, null);
        backGroundTop = (backGroundTop + 1) % Main.WINDOW_HEIGHT;
        
        // 子弹显示在飞机的下层
        enemyBullets.forEach(obj -> drawFlyingObject(g2d, obj));
        heroBullets.forEach(obj -> drawFlyingObject(g2d, obj));
        enemyAircrafts.forEach(obj -> drawFlyingObject(g2d, obj));
        props.forEach(obj -> drawFlyingObject(g2d, obj));
        drawFlyingObject(g2d, HeroAircraft.getInstance());
        
        //绘制时间、得分和生命值
        drawUI(g2d);
    }
    
    private void drawFlyingObject(Graphics2D g2d, AbstractFlyingObject object) {
        BufferedImage image = object.getImage();
        g2d.drawImage(image, object.getLocationX() - image.getWidth() / 2, object.getLocationY() - image.getHeight() / 2, null);
    }
    
    private void drawUI(Graphics2D g2d) {
        String timeText = "%02d:%02d:%02d".formatted(time / 1000 / 60, time / 1000 % 60, time % 1000 / 10);
        String scoreText = "SCORE: " + score;
        String healthText = "HP: " + HeroAircraft.getInstance().getHealth();
        
        g2d.setFont(new Font("SansSerif", Font.BOLD, 23));
        
        int x = 10;
        int y = 10;
        drawShadowedText(g2d, timeText, x, y, 3, 3);
        y += 30;
        drawShadowedText(g2d, scoreText, x, y, 3, 3);
        y += 30;
        drawShadowedText(g2d, healthText, x, y, 3, 3);
        
        if (gameOver) {
            g2d.setColor(new Color(45, 35, 35, 200));
            g2d.fillRect(0, Main.WINDOW_HEIGHT / 3, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT / 3);
            g2d.setFont(new Font("SansSerif", Font.BOLD, 60));
            drawShadowedText(g2d, "GAME OVER", 0, 0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, 3, 6);
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
