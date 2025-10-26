package pers.hpcx.aircraft;

import pers.hpcx.app.ResourceManager;

/**
 * 普通敌机
 * 不可射击
 *
 * @author fengyang
 */
public class MobEnemy extends AbstractAircraft {
    
    public MobEnemy(int health) {
        super(health, ResourceManager.MOB_ENEMY_IMAGE);
    }
    
    @Override public void onBombExplosion() {
        decreaseHp(Integer.MAX_VALUE);
    }
}
