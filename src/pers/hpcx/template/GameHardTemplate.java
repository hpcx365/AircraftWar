package pers.hpcx.template;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.app.ResourceManager;

import java.awt.image.BufferedImage;

public class GameHardTemplate extends GameEasyTemplate {
    
    @Override public BufferedImage background() {
        return ResourceManager.BACKGROUND_IMAGES.getLast();
    }
    
    @Override public long initialEnemySpawnInterval(Class<? extends AbstractAircraft> enemyType) {
        return super.initialEnemySpawnInterval(enemyType) * 2 / 3;
    }
    
    @Override public int initialEnemyMaxHealth(Class<? extends AbstractAircraft> enemyType) {
        return super.initialEnemyMaxHealth(enemyType) * 3 / 2;
    }
}
