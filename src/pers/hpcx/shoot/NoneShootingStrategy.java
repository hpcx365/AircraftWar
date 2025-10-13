package pers.hpcx.shoot;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.bullet.BaseBullet;

import java.util.List;

public final class NoneShootingStrategy<B extends BaseBullet> implements ShootingStrategy<B> {
    
    private static final NoneShootingStrategy<?> INSTANCE = new NoneShootingStrategy<>();
    
    private NoneShootingStrategy() {
    }
    
    @SuppressWarnings("unchecked")
    public static <B extends BaseBullet> NoneShootingStrategy<B> getInstance() {
        return (NoneShootingStrategy<B>) INSTANCE;
    }
    
    @Override public List<B> shoot(AbstractAircraft shooter) {
        return List.of();
    }
}
