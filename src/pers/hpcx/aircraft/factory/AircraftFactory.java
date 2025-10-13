package pers.hpcx.aircraft.factory;

import pers.hpcx.aircraft.AbstractAircraft;

@FunctionalInterface
public interface AircraftFactory {
    
    AbstractAircraft create();
}
