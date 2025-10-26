package pers.hpcx.fire;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.bullet.BaseBullet;

import java.util.List;

@FunctionalInterface
public interface FireStrategy<B extends BaseBullet> {
    
    List<B> shoot(AbstractAircraft shooter);
}
