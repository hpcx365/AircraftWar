package pers.hpcx.buff;

import pers.hpcx.app.GamePanel;

public class SuperBulletBuff extends BulletBuff {
    
    public SuperBulletBuff(int expirationTime) {
        super(expirationTime);
    }
    
    @Override public void update(int deltaTime, GamePanel gamePanel) {
        super.update(deltaTime, gamePanel);
        gamePanel.getHeroAircraft().setFireStrategy(gamePanel.getTemplate().heroSupurFireStrategy());
    }
}
