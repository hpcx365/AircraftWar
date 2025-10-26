package pers.hpcx.fire;

import lombok.Value;
import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Value public class StraightFireStrategy<B extends BaseBullet> implements FireStrategy<B> {
    
    int num;
    Supplier<B> supplier;
    double speedX;
    double speedY;
    
    @Override public List<B> shoot(AbstractAircraft shooter) {
        List<B> res = new ArrayList<>(num);
        
        for (int i = 0; i < num; i++) {
            double x = (i - 0.5 * (num - 1)) * 20;
            B bullet = supplier.get();
            bullet.setLocationX(x + shooter.getLocationX());
            bullet.setLocationY(shooter.getLocationY());
            bullet.setSpeedX(speedX + shooter.getSpeedX());
            bullet.setSpeedY(speedY + shooter.getSpeedY());
            res.add(bullet);
        }
        
        return res;
    }
}
