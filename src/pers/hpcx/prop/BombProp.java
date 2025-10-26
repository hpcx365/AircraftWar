package pers.hpcx.prop;

import pers.hpcx.app.GamePanel;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.basic.AbstractFlyingObject;

import java.util.ArrayList;
import java.util.List;

public class BombProp extends BaseProp {
    
    public BombProp() {
        super(ResourceManager.BOMB_PROP_IMAGE);
    }
    
    @Override public void takeEffect(GamePanel gamePanel) {
        List<AbstractFlyingObject> objects = new ArrayList<>();
        objects.addAll(gamePanel.getEnemyBullets());
        objects.addAll(gamePanel.getEnemyAircrafts());
        objects.forEach(AbstractFlyingObject::onBombExplosion);
        gamePanel.play(ResourceManager.BOMB_EXPLOSION_AUDIO);
    }
}
