package edu.hitsz.prop.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BulletProp;

public class BombPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new BulletProp(0, 0, 0, 0, 1);
    }
}
