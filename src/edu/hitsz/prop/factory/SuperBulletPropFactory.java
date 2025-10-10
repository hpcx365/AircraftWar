package edu.hitsz.prop.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.BulletProp;

public class SuperBulletPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new BulletProp();
    }
}
