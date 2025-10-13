package pers.hpcx.prop.factory;

import pers.hpcx.prop.BaseProp;
import pers.hpcx.prop.HealthProp;

public class HealthPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new HealthProp();
    }
}
