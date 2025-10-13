package pers.hpcx.prop;

import pers.hpcx.app.GamePanel;
import pers.hpcx.app.ResourceManager;

public class BombProp extends BaseProp {
    
    public BombProp() {
        super(ResourceManager.BOMB_PROP_IMAGE);
    }
    
    @Override public void takeEffect(GamePanel gamePanel) {
        gamePanel.getEnemyAircrafts().forEach(a -> a.decreaseHp(500));
        gamePanel.play(ResourceManager.BOMB_EXPLOSION_AUDIO);
    }
}
