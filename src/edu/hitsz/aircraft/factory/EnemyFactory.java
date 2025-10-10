package edu.hitsz.aircraft.factory;

import edu.hitsz.aircraft.AbstractAircraft;

@FunctionalInterface
public interface EnemyFactory {
    
    AbstractAircraft createEnemy();
}
