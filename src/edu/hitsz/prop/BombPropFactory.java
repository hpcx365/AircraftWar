package edu.hitsz.prop;

public class BombPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new BulletProp(0, 0, 0, 0, 1);
    }
}
