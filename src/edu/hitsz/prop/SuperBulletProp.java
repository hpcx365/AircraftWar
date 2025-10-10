package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shoot.RadialShootingStrategy;

public class SuperBulletProp extends BaseProp {
    
    @Override public void takeEffect(HeroAircraft heroAircraft) {
        heroAircraft.setShootingStrategy(new RadialShootingStrategy<>(32, () -> new HeroBullet(50), 20));
    }
}
