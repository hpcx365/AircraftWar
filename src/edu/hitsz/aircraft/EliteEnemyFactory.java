package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.util.RandomTimedTrigger;
import pers.hpcx.util.Random;

public class EliteEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        EliteEnemy enemy = new EliteEnemy(0, 0, 0, Random.getInstance().nextRange(1, 3), 300, new RandomTimedTrigger(600, 1000));
        enemy.setLocationX(Random.getInstance().nextRange(enemy.getWidth() / 2, Main.WINDOW_WIDTH - enemy.getWidth() / 2));
        enemy.setLocationY(-enemy.getHeight() / 2);
        return enemy;
    }
}
