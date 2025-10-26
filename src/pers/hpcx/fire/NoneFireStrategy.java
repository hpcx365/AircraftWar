package pers.hpcx.fire;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.bullet.BaseBullet;

import java.util.List;

public final class NoneFireStrategy<B extends BaseBullet> implements FireStrategy<B> {
    
    private static final NoneFireStrategy<?> INSTANCE = new NoneFireStrategy<>();
    
    private NoneFireStrategy() {
    }
    
    @SuppressWarnings("unchecked")
    public static <B extends BaseBullet> NoneFireStrategy<B> getInstance() {
        return (NoneFireStrategy<B>) INSTANCE;
    }
    
    @Override public List<B> shoot(AbstractAircraft shooter) {
        return List.of();
    }
}
