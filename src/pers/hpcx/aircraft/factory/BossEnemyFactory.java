package pers.hpcx.aircraft.factory;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.aircraft.BossEnemy;
import pers.hpcx.app.Main;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.bullet.EnemyBullet;
import pers.hpcx.shoot.RingShootingStrategy;
import pers.hpcx.trigger.TimedTrigger;

public class BossEnemyFactory implements AircraftFactory {
    
    @Override public AbstractAircraft create() {
        BossEnemy enemy = new BossEnemy(10000);
        enemy.setImage(ResourceManager.BOSS_ENEMY_IMAGE);
        enemy.setLocationX(Main.WINDOW_WIDTH * 0.5);
        enemy.setLocationY(-enemy.getHeight() * 0.5);
        enemy.setShootingTrigger(new TimedTrigger(1000));
        enemy.setShootingStrategy(new RingShootingStrategy<>(20, () -> new EnemyBullet(50), 100, 0, 10));
        return enemy;
    }
}
