package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.SuperEliteEnemy;
import edu.hitsz.application.Main;
import edu.hitsz.util.RandomTimedTrigger;
import pers.hpcx.util.Random;

public class SuperEliteEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        int vx = Random.getInstance().choice(new int[] {1, -1});
        RandomTimedTrigger shootTrigger = new RandomTimedTrigger(1000, 1200);
        SuperEliteEnemy enemy = new SuperEliteEnemy(0, 0, vx, 1, 500, shootTrigger);
        enemy.setLocationX(Random.getInstance().nextRange(enemy.getWidth() / 2, Main.WINDOW_WIDTH - enemy.getWidth() / 2));
        enemy.setLocationY(-enemy.getHeight() / 2);
        return enemy;
    }
}
