package pers.hpcx.prop.factory;

import pers.hpcx.prop.BaseProp;
import pers.hpcx.prop.SuperBulletProp;

public class SuperBulletPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new SuperBulletProp();
    }
}
