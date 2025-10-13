package pers.hpcx.prop.factory;

import pers.hpcx.prop.BaseProp;
import pers.hpcx.prop.BulletProp;

public class BulletPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new BulletProp();
    }
}
