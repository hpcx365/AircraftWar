package edu.hitsz.prop;

public class HealthPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new HealthProp(0, 0, 0, 0, 100);
    }
}
