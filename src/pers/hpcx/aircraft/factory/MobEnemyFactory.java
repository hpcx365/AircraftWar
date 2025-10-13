package pers.hpcx.aircraft.factory;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.aircraft.MobEnemy;
import pers.hpcx.app.Main;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.shoot.NoneShootingStrategy;
import pers.hpcx.trigger.TimedTrigger;

import java.util.concurrent.ThreadLocalRandom;

public class MobEnemyFactory implements AircraftFactory {
    
    @Override public AbstractAircraft create() {
        MobEnemy enemy = new MobEnemy(100);
        enemy.setImage(ResourceManager.MOB_ENEMY_IMAGE);
        enemy.setSpeedX(0);
        enemy.setSpeedY(ThreadLocalRandom.current().nextDouble(2, 6));
        enemy.setLocationX(ThreadLocalRandom.current().nextDouble(enemy.getWidth() * 0.5, Main.WINDOW_WIDTH - enemy.getWidth() * 0.5));
        enemy.setLocationY(-enemy.getHeight() * 0.5);
        enemy.setShootingTrigger(new TimedTrigger(10000));
        enemy.setShootingStrategy(NoneShootingStrategy.getInstance());
        return enemy;
    }
}
