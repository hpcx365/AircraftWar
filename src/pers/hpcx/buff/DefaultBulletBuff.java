package pers.hpcx.buff;

import pers.hpcx.aircraft.HeroAircraft;
import pers.hpcx.app.GamePanel;
import pers.hpcx.bullet.HeroBullet;
import pers.hpcx.fire.FireStrategy;
import pers.hpcx.fire.StraightFireStrategy;

public class DefaultBulletBuff extends BaseBuff {
    
    public DefaultBulletBuff() {
        super(-1);
    }
    
    private static final FireStrategy<?> SHOOTING_STRATEGY =
        new StraightFireStrategy<>(1, () -> new HeroBullet(50), 0, -20);
    
    @Override public void update(int deltaTime, GamePanel gamePanel) {
        super.update(deltaTime, gamePanel);
        HeroAircraft hero = gamePanel.getHeroAircraft();
        if (hero.getBuffs().stream().anyMatch(b -> b instanceof BulletBuff)) {
            return;
        }
        hero.setFireStrategy(SHOOTING_STRATEGY);
    }
}
