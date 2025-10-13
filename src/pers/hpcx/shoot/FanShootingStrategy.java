package pers.hpcx.shoot;

import lombok.Value;
import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.bullet.BaseBullet;
import pers.hpcx.util.Ranges;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Value public class FanShootingStrategy<B extends BaseBullet> implements ShootingStrategy<B> {
    
    int num;
    Supplier<B> supplier;
    double angle;
    double direction;
    double speed;
    
    @Override public List<B> shoot(AbstractAircraft shooter) {
        List<B> res = new ArrayList<>(num);
        
        for (int i = 0; i < num; i++) {
            double theta = num == 1 ? direction : Ranges.map(i, 0, num - 1, direction - 0.5 * angle, direction + 0.5 * angle);
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
