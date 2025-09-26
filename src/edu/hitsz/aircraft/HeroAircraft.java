package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.util.RandomTimedTrigger;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author hitsz
 * @author fengyang
 */
@Getter @Setter public class HeroAircraft extends AbstractAircraft {
    
    // 懒汉式单例模式
    private static volatile HeroAircraft INSTANCE;
    
    // 子弹伤害
    private int power;
    
    // 子弹一次发射数量
    private int shootNum;
    
    private HeroAircraft(int locationX, int locationY, int hp, RandomTimedTrigger shootTrigger, int power, int shootNum) {
        super(locationX, locationY, 0, 0, hp, shootTrigger);
        this.power = power;
        this.shootNum = shootNum;
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
                    INSTANCE = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                            1000, new RandomTimedTrigger(100, 100), 20, 1
                    );
                }
            }
        }
        return INSTANCE;
    }
    
    @Override public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
        throw new UnsupportedOperationException();
    }
    
    /**
     * 通过射击产生子弹
     *
     * @return 射击出的子弹List
     */
    @Override public List<BaseBullet> shoot() {
        int x = locationX;
        int y = locationY - 2;
        int vx = 0;
        int vy = speedY - 20;
        
        List<BaseBullet> res = new LinkedList<>();
        for (int i = 0; i < shootNum; i++) {
            res.add(new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, vx, vy, power));
        }
        return res;
    }
}
