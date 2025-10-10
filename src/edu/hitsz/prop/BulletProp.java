package edu.hitsz.prop;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.shoot.FanShootingStrategy;
import lombok.Getter;

/**
 * @author fengyang
 */
@Getter public class BulletProp extends BaseProp {
    
    @Override public void takeEffect(HeroAircraft heroAircraft) {
        heroAircraft.setShootingStrategy(new FanShootingStrategy<>(3, () -> new HeroBullet(50), Math.toRadians(30), -0.5 * Math.PI, 20));
    }
}
