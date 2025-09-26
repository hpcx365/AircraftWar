package edu.hitsz.prop;

public class BulletPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new BombProp(0, 0, 0, 0);
    }
}
