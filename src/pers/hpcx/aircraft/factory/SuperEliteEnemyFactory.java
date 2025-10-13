package pers.hpcx.aircraft.factory;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.aircraft.SuperEliteEnemy;
import pers.hpcx.app.Main;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.bullet.EnemyBullet;
import pers.hpcx.shoot.FanShootingStrategy;
import pers.hpcx.trigger.TimedTrigger;

import java.util.concurrent.ThreadLocalRandom;

public class SuperEliteEnemyFactory implements AircraftFactory {
    
    @Override public AbstractAircraft create() {
        int vx = ThreadLocalRandom.current().nextDouble() < 0.5 ? 1 : -1;
        SuperEliteEnemy enemy = new SuperEliteEnemy(500);
        enemy.setImage(ResourceManager.SUPER_ELITE_ENEMY_IMAGE);
        enemy.setLocationX(ThreadLocalRandom.current().nextDouble(enemy.getWidth() * 0.5, Main.WINDOW_WIDTH - enemy.getWidth() * 0.5));
        enemy.setLocationY(-enemy.getHeight() * 0.5);
        enemy.setSpeedX(ThreadLocalRandom.current().nextGaussian());
        enemy.setSpeedY(1 + ThreadLocalRandom.current().nextDouble());
        enemy.setShootingTrigger(new TimedTrigger(1200));
        enemy.setShootingStrategy(new FanShootingStrategy<>(
            ThreadLocalRandom.current().nextInt(3, 6), () -> new EnemyBullet(20), Math.toRadians(60), 0.5 * Math.PI, 5));
        return enemy;
    }
}
