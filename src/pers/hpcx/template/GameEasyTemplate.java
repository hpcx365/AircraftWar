package pers.hpcx.template;

import pers.hpcx.aircraft.AbstractAircraft;
import pers.hpcx.aircraft.BossEnemy;
import pers.hpcx.aircraft.EliteEnemy;
import pers.hpcx.aircraft.SuperEliteEnemy;
import pers.hpcx.app.ResourceManager;

import java.awt.image.BufferedImage;

public class GameEasyTemplate implements GameTemplate {
    
    @Override public BufferedImage background() {
        return ResourceManager.BACKGROUND_IMAGES.getFirst();
    }
    
    @Override public long rateUpdateInterval() {
        return 20000;
    }
    
    @Override public double enemySpawnFrequencyGrowthRate() {
        return 0.05;
    }
    
    @Override public double enemyMaxHealthGrowthRate() {
        return 0.05;
    }
    
    @Override public double enemyBulletPowerGrowthRate() {
        return 0.05;
    }
    
    @Override public double enemyMaxSpeedGrowthRate() {
        return 0.05;
    }
    
    @Override public double enemyFireFrequencyGrowthRate() {
        return 0.05;
    }
    
    @Override public double enemyBulletSpeedGrowthRate() {
        return 0.05;
    }
    
    @Override public long initialEnemySpawnInterval(Class<? extends AbstractAircraft> enemyType) {
        if (enemyType == EliteEnemy.class) {
            return 6000;
        }
        if (enemyType == SuperEliteEnemy.class) {
            return 10000;
        }
        return 1000;
    }
    
    @Override public int initialEnemyMaxHealth(Class<? extends AbstractAircraft> enemyType) {
        if (enemyType == EliteEnemy.class) {
            return 400;
        }
        if (enemyType == SuperEliteEnemy.class) {
            return 600;
        }
        if (enemyType == BossEnemy.class) {
            return 20000;
        }
        return 200;
    }
    
    @Override public int initialEnemyBulletPower(Class<? extends AbstractAircraft> enemyType) {
        return 20;
    }
    
    @Override public double initialEnemyMaxSpeed(Class<? extends AbstractAircraft> enemyType) {
        if (enemyType == SuperEliteEnemy.class) {
            return 2;
        }
        return 4;
    }
    
    @Override public long initialEnemyFireInterval(Class<? extends AbstractAircraft> enemyType) {
        if (enemyType == BossEnemy.class) {
            return 3000;
        }
        return 2000;
    }
    
    @Override public double initialEnemyBulletSpeed(Class<? extends AbstractAircraft> enemyType) {
        return 3;
    }
}
