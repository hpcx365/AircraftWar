package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.shoot.NoneShootingStrategy;
import edu.hitsz.util.TimedTrigger;

import java.util.concurrent.ThreadLocalRandom;

public class MobEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        MobEnemy enemy = new MobEnemy(100);
        enemy.setSpeedX(0);
        enemy.setSpeedY(ThreadLocalRandom.current().nextDouble(2, 6));
        enemy.setLocationX(ThreadLocalRandom.current().nextDouble(enemy.getWidth() * 0.5, Main.WINDOW_WIDTH - enemy.getWidth() * 0.5));
        enemy.setLocationY(-enemy.getHeight() * 0.5);
        enemy.setShootingTrigger(new TimedTrigger(10000));
        enemy.setShootingStrategy(NoneShootingStrategy.getInstance());
        return enemy;
    }
}
