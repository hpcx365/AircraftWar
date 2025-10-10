package edu.hitsz.shoot;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

@FunctionalInterface
public interface ShootingStrategy<B extends BaseBullet> {
    
    List<B> shoot(AbstractAircraft shooter);
}
