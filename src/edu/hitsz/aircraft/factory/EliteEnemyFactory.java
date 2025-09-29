package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.util.RandomTimedTrigger;
import pers.hpcx.util.Random;

public class EliteEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        int vy = Random.getInstance().nextRange(1, 3);
        RandomTimedTrigger shootTrigger = new RandomTimedTrigger(600, 1000);
        EliteEnemy enemy = new EliteEnemy(0, 0, 0, vy, 300, shootTrigger);
        enemy.setLocationX(Random.getInstance().nextRange(enemy.getWidth() / 2, Main.WINDOW_WIDTH - enemy.getWidth() / 2));
        enemy.setLocationY(-enemy.getHeight() / 2);
        return enemy;
    }
}
