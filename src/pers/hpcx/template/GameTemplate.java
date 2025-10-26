package pers.hpcx.template;

import pers.hpcx.aircraft.*;
import pers.hpcx.bullet.EnemyBullet;
import pers.hpcx.bullet.HeroBullet;
import pers.hpcx.fire.*;
import pers.hpcx.prop.*;

import java.awt.image.BufferedImage;
import java.util.Random;

public interface GameTemplate {
    
    BufferedImage background();
    
    long rateUpdateInterval();
    
    double enemySpawnFrequencyGrowthRate();
    
    double enemyMaxHealthGrowthRate();
    
    double enemyBulletPowerGrowthRate();
    
    double enemyMaxSpeedGrowthRate();
    
    double enemyFireFrequencyGrowthRate();
    
    double enemyBulletSpeedGrowthRate();
    
    long initialEnemySpawnInterval(Class<? extends AbstractAircraft> enemyType);
    
    int initialEnemyMaxHealth(Class<? extends AbstractAircraft> enemyType);
    
    int initialEnemyBulletPower(Class<? extends AbstractAircraft> enemyType);
    
    double initialEnemyMaxSpeed(Class<? extends AbstractAircraft> enemyType);
    
    long initialEnemyFireInterval(Class<? extends AbstractAircraft> enemyType);
    
    double initialEnemyBulletSpeed(Class<? extends AbstractAircraft> enemyType);
    
    default int scoreOnEnemyDestroy(Class<? extends AbstractAircraft> enemyType) {
        if (enemyType == EliteEnemy.class) {
            return 50;
        }
        if (enemyType == SuperEliteEnemy.class) {
            return 100;
        }
        if (enemyType == BossEnemy.class) {
            return 200;
        }
        return 20;
    }
    
    default int bossSpawnScoreGap() {
        return 1000;
    }
    
    default int heroMaxHealth() {
        return 10000;
    }
    
    default int heroBulletPower() {
        return 50;
    }
    
    default long heroFireInterval() {
        return 100;
    }
    
    default double heroBulletSpeed() {
        return 20;
    }
    
    default FireStrategy<HeroBullet> heroDefaultFireStrategy() {
        return new StraightFireStrategy<>(1, () -> new HeroBullet(heroBulletPower()), 0, -heroBulletSpeed());
    }
    
    default FireStrategy<HeroBullet> heroEnhancedFireStrategy() {
        return new FanFireStrategy<>(5, () -> new HeroBullet(heroBulletPower()), Math.toRadians(30), -0.5 * Math.PI, heroBulletSpeed());
    }
    
    default FireStrategy<HeroBullet> heroSupurFireStrategy() {
        return new RadialFireStrategy<>(32, () -> new HeroBullet(heroBulletPower()), heroBulletSpeed());
    }
    
    default int numPropsOnEnemyDestroy(Class<? extends AbstractAircraft> enemyType, Random random) {
        if (enemyType == EliteEnemy.class) {
            return random.nextInt(2);
        }
        if (enemyType == SuperEliteEnemy.class) {
            return random.nextInt(3);
        }
        if (enemyType == BossEnemy.class) {
            return random.nextInt(4);
        }
        return 0;
    }
    
    default BaseProp choosePropOnEnemyDestroy(Class<? extends AbstractAircraft> enemyType, Random random) {
        double rnd = random.nextDouble();
        if (rnd < 0.30) {
            return new HealthProp();
        } else if (rnd < 0.60) {
            return new BulletProp();
        } else if (rnd < 0.90) {
            return new SuperBulletProp();
        } else {
            return new BombProp();
        }
    }
    
    default int crashEnemyDamage(Class<? extends AbstractAircraft> enemyType) {
        if (enemyType == MobEnemy.class) {
            return 50;
        }
        if (enemyType == EliteEnemy.class) {
            return 100;
        }
        if (enemyType == SuperEliteEnemy.class) {
            return 200;
        }
        return Integer.MAX_VALUE;
    }
    
    default FireStrategy<EnemyBullet> enemyFireStrategy(Class<? extends AbstractAircraft> enemyType, int bulletPower, double bulletSpeed) {
        if (enemyType == EliteEnemy.class) {
            return new StraightFireStrategy<>(2, () -> new EnemyBullet(bulletPower), 0, bulletSpeed);
        }
        if (enemyType == SuperEliteEnemy.class) {
            return new FanFireStrategy<>(4, () -> new EnemyBullet(bulletPower), Math.toRadians(60), 0.5 * Math.PI, bulletSpeed);
        }
        if (enemyType == BossEnemy.class) {
            return new RingFireStrategy<>(10, () -> new EnemyBullet(bulletPower), 100, 0, bulletSpeed);
        }
        return NoneFireStrategy.getInstance();
    }
}
