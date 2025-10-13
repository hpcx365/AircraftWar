package pers.hpcx.shoot;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.bullet.BaseBullet;

import java.util.List;

@FunctionalInterface
public interface ShootingStrategy<B extends BaseBullet> {
    
    List<B> shoot(AbstractAircraft shooter);
}
