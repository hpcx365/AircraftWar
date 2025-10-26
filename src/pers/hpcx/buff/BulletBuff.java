package pers.hpcx.buff;

import pers.hpcx.aircraft.HeroAircraft;
import pers.hpcx.app.GamePanel;

public class BulletBuff extends BaseBuff {
    
    public BulletBuff(int expirationTime) {
        super(expirationTime);
    }
    
    @Override public void update(int deltaTime, GamePanel gamePanel) {
        super.update(deltaTime, gamePanel);
        HeroAircraft hero = gamePanel.getHeroAircraft();
        if (hero.getBuffs().stream().anyMatch(b -> b instanceof SuperBulletBuff)) {
            return;
        }
        hero.setFireStrategy(gamePanel.getTemplate().heroEnhancedFireStrategy());
    }
}
