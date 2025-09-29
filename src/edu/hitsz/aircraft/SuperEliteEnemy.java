package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.util.RandomTimedTrigger;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 超级精英敌机
 * 可扇形射击，左右移动
 *
 * @author fengyang
 */
@Getter @Setter public class SuperEliteEnemy extends AbstractAircraft {
    
    // 子弹一次发射数量
    private int shootNum = 3;
    
    // 子弹伤害
    private int power = 50;
    
    public SuperEliteEnemy(int locationX, int locationY, int speedX, int speedY, int health, RandomTimedTrigger shootTrigger) {
        super(locationX, locationY, speedX, speedY, health, shootTrigger);
    }
    
    @Override public List<BaseBullet> shoot() {
        int x = locationX;
        int y = locationY + 2;
        int vx = 2;
        int vy = speedY + 5;
        
        List<BaseBullet> res = new ArrayList<>();
        for (int i = 0; i < shootNum; i++) {
            res.add(new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, (i * 2 - shootNum + 1) * vx, vy, power));
        }
        return res;
    }
}
