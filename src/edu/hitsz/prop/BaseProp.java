package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;

/**
 * 道具基类<br>
 * 可以实现不同类型的道具
 *
 * @author fengyang
 */
public abstract class BaseProp extends AbstractFlyingObject {
    
    protected BaseProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }
    
    public abstract void takeEffect(HeroAircraft heroAircraft);
}
