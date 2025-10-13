package pers.hpcx.aircraft.factory;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.aircraft.EliteEnemy;
import pers.hpcx.app.Main;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.bullet.EnemyBullet;
import pers.hpcx.shoot.StraightShootingStrategy;
import pers.hpcx.trigger.TimedTrigger;

import java.util.concurrent.ThreadLocalRandom;

public class EliteEnemyFactory implements AircraftFactory {
    
    @Override public AbstractAircraft create() {
        double speedY = ThreadLocalRandom.current().nextDouble(1, 3);
        EliteEnemy enemy = new EliteEnemy(300);
        enemy.setImage(ResourceManager.ELITE_ENEMY_IMAGE);
        enemy.setLocationX(ThreadLocalRandom.current().nextDouble(enemy.getWidth() * 0.5, Main.WINDOW_WIDTH - enemy.getWidth() * 0.5));
        enemy.setLocationY(-enemy.getHeight() * 0.5);
        enemy.setSpeedX(0);
        enemy.setSpeedY(speedY);
        enemy.setShootingTrigger(new TimedTrigger(800));
        enemy.setShootingStrategy(new StraightShootingStrategy<>(2, () -> new EnemyBullet(10), 0, speedY * 2));
        return enemy;
    }
}
