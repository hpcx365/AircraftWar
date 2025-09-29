package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.util.RandomTimedTrigger;
import lombok.Getter;
import lombok.Setter;
import pers.hpcx.util.Ranges;

import java.util.ArrayList;
import java.util.List;

/**
 * Boss敌机
 * 可环形射击，悬浮
 *
 * @author fengyang
 */
@Getter @Setter public class BossEnemy extends AbstractAircraft {
    
    // 子弹一次发射数量
    private int shootNum = 20;
    
    // 子弹伤害
    private int power = 30;
    
    // 是否正在悬浮射击
    private boolean hovering = false;
    
    public BossEnemy(int locationX, int locationY, int health, RandomTimedTrigger shootTrigger) {
        super(locationX, locationY, 0, 0, health, shootTrigger);
    }
    
    @Override public void forward() {
        if (!hovering) {
            if (getLocationY() < Main.WINDOW_HEIGHT / 5) {
                speedX = 0;
                speedY = 1;
            } else {
                hovering = true;
                speedX = 1;
                speedY = 0;
            }
        }
        super.forward();
    }
    
    @Override public List<BaseBullet> shoot() {
        if (!hovering) {
            return List.of();
        }
        
        List<BaseBullet> res = new ArrayList<>();
        
        for (int i = 0; i < shootNum; i++) {
            double theta = Ranges.map(i, 0, shootNum, 0, Math.TAU);
            int x = (int) Math.round(getLocationX() + 100 * Math.cos(theta));
            int y = (int) Math.round(getLocationY() + 100 * Math.sin(theta));
            res.add(new EnemyBullet(x, y, 0, 5, power));
        }
        
        return res;
    }
}
