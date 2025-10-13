package pers.hpcx.prop;

import lombok.Getter;
import pers.hpcx.app.GamePanel;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.buff.BulletBuff;

@Getter public class BulletProp extends BaseProp {
    
    public BulletProp() {
        super(ResourceManager.BULLET_PROP_IMAGE);
    }
    
    @Override public void takeEffect(GamePanel gamePanel) {
        gamePanel.getHeroAircraft().addBuff(new BulletBuff(3000));
    }
}
