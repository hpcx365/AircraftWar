package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;

public class BombProp extends BaseProp {
    
    @Override public void takeEffect(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
    }
}
