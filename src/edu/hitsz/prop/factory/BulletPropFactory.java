package edu.hitsz.prop.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BombProp;

public class BulletPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new BombProp();
    }
}
