package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.Main;
import pers.hpcx.util.Random;

public class MobEnemyFactory implements EnemyFactory {
    
    @Override public AbstractAircraft createEnemy() {
        MobEnemy enemy = new MobEnemy(0, 0, 0, Random.getInstance().nextRange(2, 6), 100);
        enemy.setLocationX(Random.getInstance().nextRange(enemy.getWidth() / 2, Main.WINDOW_WIDTH - enemy.getWidth() / 2));
        enemy.setLocationY(-enemy.getHeight() / 2);
        return enemy;
    }
}
