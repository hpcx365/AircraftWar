package pers.hpcx.buff;

import pers.hpcx.aircraft.HeroAircraft;
import pers.hpcx.app.GamePanel;
import pers.hpcx.bullet.HeroBullet;
import pers.hpcx.shoot.FanShootingStrategy;
import pers.hpcx.shoot.ShootingStrategy;

public class BulletBuff extends BaseBuff {
    
    public BulletBuff(int expirationTime) {
        super(expirationTime);
    }
    
    private static final ShootingStrategy<?> SHOOTING_STRATEGY =
        new FanShootingStrategy<>(3, () -> new HeroBullet(50), Math.toRadians(30), -0.5 * Math.PI, 20);
    
    @Override public void update(int deltaTime, GamePanel gamePanel) {
        super.update(deltaTime, gamePanel);
        HeroAircraft hero = gamePanel.getHeroAircraft();
        if (hero.getBuffs().stream().anyMatch(b -> b instanceof SuperBulletBuff)) {
            return;
        }
        hero.setShootingStrategy(SHOOTING_STRATEGY);
    }
}
