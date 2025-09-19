package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.util.RandomTimedTrigger;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 精英敌机
 * 可射击
 *
 * @author fengyang
 */
@Getter @Setter public class EliteEnemy extends AbstractAircraft {
    
    // 子弹一次发射数量
    private int shootNum = 2;
    
    // 子弹伤害
    private int power = 30;
    
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, RandomTimedTrigger shootTrigger) {
        super(locationX, locationY, speedX, speedY, hp, shootTrigger);
    }
    
    @Override public List<BaseBullet> shoot() {
        int x = locationX;
        int y = locationY + 2;
        int vx = 0;
        int vy = speedY + 5;
        
        List<BaseBullet> res = new ArrayList<>();
        for (int i = 0; i < shootNum; i++) {
            res.add(new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, vx, vy, power));
        }
        return res;
    }
}
