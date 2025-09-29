package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import lombok.Getter;

/**
 * @author fengyang
 */
@Getter public class BulletProp extends BaseProp {
    
    private final int numBullet;
    
    public BulletProp(int locationX, int locationY, int speedX, int speedY, int numBullet) {
        super(locationX, locationY, speedX, speedY);
        this.numBullet = numBullet;
    }
    
    @Override public void takeEffect(HeroAircraft heroAircraft) {
        System.out.println("FireSupply active!");
        heroAircraft.setShootNum(Math.min(heroAircraft.getShootNum() + numBullet, 5));
    }
}
