package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shoot.StraightShootingStrategy;
import edu.hitsz.util.TimedTrigger;
import lombok.Getter;
import lombok.Setter;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author hitsz
 * @author fengyang
 */
@Getter @Setter public class HeroAircraft extends AbstractAircraft {
    
    // 懒汉式单例模式
    private static volatile HeroAircraft INSTANCE;
    
    private HeroAircraft(int health) {
        super(health);
    }
    
    /**
     * 获取英雄机单例
     *
     * @return 英雄机实例
     */
    public static HeroAircraft getInstance() {
        // 双重检查锁定
        if (INSTANCE == null) {
            synchronized (HeroAircraft.class) {
                if (INSTANCE == null) {
                    HeroAircraft instance = new HeroAircraft(1000);
                    instance.setLocationX(Main.WINDOW_WIDTH * 0.5);
                    instance.setLocationY(Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight());
                    instance.setShootingTrigger(new TimedTrigger(100));
                    instance.setShootingStrategy(new StraightShootingStrategy<>(1, () -> new HeroBullet(50), 0, -20));
                    INSTANCE = instance;
                }
            }
        }
        return INSTANCE;
    }
    
    @Override public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
        throw new UnsupportedOperationException();
    }
}
