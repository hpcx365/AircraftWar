package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.shoot.RingShootingStrategy;
import edu.hitsz.util.TimedTrigger;

public class BossEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        BossEnemy enemy = new BossEnemy(10000);
        enemy.setLocationX(Main.WINDOW_WIDTH * 0.5);
        enemy.setLocationY(-enemy.getHeight() * 0.5);
        enemy.setShootingTrigger(new TimedTrigger(1000));
        enemy.setShootingStrategy(new RingShootingStrategy<>(20, () -> new EnemyBullet(50), 100, 0, 10));
        return enemy;
    }
}
