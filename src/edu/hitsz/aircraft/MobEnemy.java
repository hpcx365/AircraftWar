package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 * @author fengyang
 */
public class MobEnemy extends AbstractAircraft {
    
    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp, null);
    }
    
    @Override public List<BaseBullet> shoot() {
        return List.of();
    }
}
