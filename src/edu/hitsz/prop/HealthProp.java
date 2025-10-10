package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import lombok.Getter;

/**
 * @author fengyang
 */
@Getter public class HealthProp extends BaseProp {
    
    @Override public void takeEffect(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(100);
    }
}
