package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.SuperEliteEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.shoot.FanShootingStrategy;
import edu.hitsz.util.TimedTrigger;

import java.util.concurrent.ThreadLocalRandom;

public class SuperEliteEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        int vx = ThreadLocalRandom.current().nextDouble() < 0.5 ? 1 : -1;
        SuperEliteEnemy enemy = new SuperEliteEnemy(500);
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
