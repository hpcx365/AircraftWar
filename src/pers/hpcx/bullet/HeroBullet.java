package pers.hpcx.bullet;

import pers.hpcx.app.ResourceManager;

public class HeroBullet extends BaseBullet {
    
    public HeroBullet(int power) {
        super(power, ResourceManager.HERO_BULLET_IMAGE);
    }
}
