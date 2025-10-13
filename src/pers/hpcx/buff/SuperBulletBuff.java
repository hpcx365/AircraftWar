package pers.hpcx.buff;

import pers.hpcx.app.GamePanel;
import pers.hpcx.bullet.HeroBullet;
import pers.hpcx.shoot.RadialShootingStrategy;
import pers.hpcx.shoot.ShootingStrategy;

public class SuperBulletBuff extends BulletBuff {
    
    public SuperBulletBuff(int expirationTime) {
        super(expirationTime);
    }
    
    private static final ShootingStrategy<?> SHOOTING_STRATEGY =
        new RadialShootingStrategy<>(32, () -> new HeroBullet(50), 20);
    
    @Override public void update(int deltaTime, GamePanel gamePanel) {
        super.update(deltaTime, gamePanel);
        gamePanel.getHeroAircraft().setShootingStrategy(SHOOTING_STRATEGY);
    }
}
