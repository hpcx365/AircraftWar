package pers.hpcx.aircraft;

import pers.hpcx.app.ResourceManager;

/**
 * 超级精英敌机
 * 可扇形射击，左右移动
 *
 * @author fengyang
 */
public class SuperEliteEnemy extends AbstractAircraft {
    
    public SuperEliteEnemy(int health) {
        super(health, ResourceManager.SUPER_ELITE_ENEMY_IMAGE);
    }
    
    @Override public void onBombExplosion() {
        decreaseHp(100);
    }
}
