package edu.hitsz.bullet;

import edu.hitsz.application.Main;

/**
 * @author hitsz
 * @author fengyang
 */
public class EnemyBullet extends BaseBullet {
    
    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }
    
    @Override public void forward() {
        locationX += speedX;
        locationY += speedY;
        if (!getBoundingBox().intersects(Main.WINDOW_AREA)) {
            vanish();
        }
    }
}
