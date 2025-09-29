package edu.hitsz.prop.factory;

import edu.hitsz.prop.BaseProp;
import edu.hitsz.prop.HealthProp;

public class HealthPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new HealthProp(0, 0, 0, 0, 100);
    }
}
