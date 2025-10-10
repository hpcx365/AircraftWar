package edu.hitsz.bullet;

import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;
import lombok.Getter;
import pers.hpcx.util.Args;

/**
 * 子弹基类<br>
 * 可以实现不同类型的子弹
 *
 * @author hitsz
 * @author fengyang
 */
@Getter public abstract class BaseBullet extends AbstractFlyingObject {
    
    private final int power;
    
    protected BaseBullet(int power) {
        Args.assertPositive(power, "power");
        this.power = power;
    }
    
    @Override public void forward() {
        locationX += speedX;
        locationY += speedY;
        if (!getBoundingBox().intersects(Main.WINDOW_AREA)) {
            vanish();
        }
    }
}
