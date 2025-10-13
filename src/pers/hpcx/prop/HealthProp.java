package pers.hpcx.prop;

import lombok.Getter;
import pers.hpcx.app.GamePanel;
import pers.hpcx.app.ResourceManager;

@Getter public class HealthProp extends BaseProp {
    
    public HealthProp() {
        super(ResourceManager.HEALTH_PROP_IMAGE);
    }
    
    @Override public void takeEffect(GamePanel gamePanel) {
        gamePanel.getHeroAircraft().increaseHp(100);
    }
}
