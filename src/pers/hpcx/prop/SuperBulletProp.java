package pers.hpcx.prop;

import pers.hpcx.app.GamePanel;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.buff.SuperBulletBuff;

public class SuperBulletProp extends BaseProp {
    
    public SuperBulletProp() {
        super(ResourceManager.SUPER_BULLET_PROP_IMAGE);
    }
    
    @Override public void takeEffect(GamePanel gamePanel) {
        gamePanel.getHeroAircraft().addBuff(new SuperBulletBuff(3000));
    }
}
