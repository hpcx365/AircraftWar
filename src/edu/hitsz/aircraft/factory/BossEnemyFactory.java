package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.util.RandomTimedTrigger;

public class BossEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        BossEnemy enemy = new BossEnemy(0, 0, 10000, new RandomTimedTrigger(1800, 2000));
        enemy.setLocationX(Main.WINDOW_WIDTH / 2);
        enemy.setLocationY(-enemy.getHeight() / 2);
        return enemy;
    }
}
