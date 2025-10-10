package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.shoot.StraightShootingStrategy;
import edu.hitsz.util.TimedTrigger;

import java.util.concurrent.ThreadLocalRandom;

public class EliteEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        double speedY = ThreadLocalRandom.current().nextDouble(1, 3);
        EliteEnemy enemy = new EliteEnemy(300);
        enemy.setLocationX(ThreadLocalRandom.current().nextDouble(enemy.getWidth() * 0.5, Main.WINDOW_WIDTH - enemy.getWidth() * 0.5));
        enemy.setLocationY(-enemy.getHeight() * 0.5);
        enemy.setSpeedX(0);
        enemy.setSpeedY(speedY);
        enemy.setShootingTrigger(new TimedTrigger(800));
        enemy.setShootingStrategy(new StraightShootingStrategy<>(2, () -> new EnemyBullet(10), 0, speedY * 2));
        return enemy;
    }
}
