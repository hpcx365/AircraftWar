package pers.hpcx.fire;

import lombok.Value;
import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.bullet.BaseBullet;
import pers.hpcx.util.Ranges;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Value public class RingFireStrategy<B extends BaseBullet> implements FireStrategy<B> {
    
    int num;
    Supplier<B> supplier;
    double radius;
    double speedX;
    double speedY;
    
    @Override public List<B> shoot(AbstractAircraft shooter) {
        List<B> res = new ArrayList<>(num);
        
        for (int i = 0; i < num; i++) {
            double theta = Ranges.map(i, num, Math.TAU);
            B bullet = supplier.get();
            bullet.setLocationX(radius * Math.cos(theta) + shooter.getLocationX());
            bullet.setLocationY(radius * Math.sin(theta) + shooter.getLocationY());
            bullet.setSpeedX(speedX + shooter.getSpeedX());
            bullet.setSpeedY(speedY + shooter.getSpeedY());
            res.add(bullet);
        }
        
        return res;
    }
}
