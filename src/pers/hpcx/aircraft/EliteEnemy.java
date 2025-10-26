package pers.hpcx.aircraft;

import pers.hpcx.app.ResourceManager;

/**
 * 精英敌机
 * 可直线射击
 *
 * @author fengyang
 */
public class EliteEnemy extends AbstractAircraft {
    
    public EliteEnemy(int health) {
        super(health, ResourceManager.ELITE_ENEMY_IMAGE);
    }
    
    @Override public void onBombExplosion() {
        decreaseHp(Integer.MAX_VALUE);
    }
}
