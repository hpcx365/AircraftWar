package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import lombok.Getter;

/**
 * @author fengyang
 */
@Getter public class HealthProp extends BaseProp {
    
    private final int health;
    
    public HealthProp(int locationX, int locationY, int speedX, int speedY, int health) {
        super(locationX, locationY, speedX, speedY);
        this.health = health;
    }
    
    @Override public void takeEffect(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(health);
    }
}
