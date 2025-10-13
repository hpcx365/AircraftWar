package pers.hpcx.aircraft.factory;

import pers.hpcx.aircraft.HeroAircraft;
import pers.hpcx.app.Main;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.buff.DefaultBulletBuff;
import pers.hpcx.trigger.TimedTrigger;

public class HeroAircraftFactory implements AircraftFactory {
    
    @Override public HeroAircraft create() {
        HeroAircraft hero = new HeroAircraft(100);
        hero.setImage(ResourceManager.HERO_IMAGE);
        hero.setLocationX(Main.WINDOW_WIDTH * 0.5);
        hero.setLocationY(Main.WINDOW_HEIGHT - hero.getHeight());
        hero.setShootingTrigger(new TimedTrigger(100));
        hero.getBuffs().add(new DefaultBulletBuff());
        return hero;
    }
}
