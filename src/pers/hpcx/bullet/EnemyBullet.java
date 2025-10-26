package pers.hpcx.bullet;

import pers.hpcx.app.ResourceManager;

public class EnemyBullet extends BaseBullet {
    
    public EnemyBullet(int power) {
        super(power, ResourceManager.ENEMY_BULLET_IMAGE);
    }
    
    @Override public void onBombExplosion() {
        setAlive(false);
    }
}
