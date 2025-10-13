package pers.hpcx.shoot;

import lombok.Value;
import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.bullet.BaseBullet;
import pers.hpcx.util.Ranges;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Value public class RadialShootingStrategy<B extends BaseBullet> implements ShootingStrategy<B> {
    
    int num;
    Supplier<B> supplier;
    double speed;
    
    @Override public List<B> shoot(AbstractAircraft shooter) {
        List<B> res = new ArrayList<>(num);
        
        for (int i = 0; i < num; i++) {
            double theta = Ranges.map(i, num, Math.TAU);
            B bullet = supplier.get();
            bullet.setLocationX(shooter.getLocationX());
            bullet.setLocationY(shooter.getLocationY());
            bullet.setSpeedX(speed * Math.cos(theta) + shooter.getSpeedX());
            bullet.setSpeedY(speed * Math.sin(theta) + shooter.getSpeedY());
            res.add(bullet);
        }
        
        return res;
    }
}
