package pers.hpcx.prop.factory;

import pers.hpcx.prop.BaseProp;
import pers.hpcx.prop.BombProp;

public class BombPropFactory implements PropFactory {
    
    @Override public BaseProp createProp() {
        return new BombProp();
    }
}
