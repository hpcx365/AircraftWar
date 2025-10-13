package pers.hpcx.bullet;

import lombok.Getter;
import pers.hpcx.app.Main;
import pers.hpcx.basic.AbstractFlyingObject;
import pers.hpcx.util.Args;

import java.awt.image.BufferedImage;

/**
 * 子弹基类<br>
 * 可以实现不同类型的子弹
 *
 * @author fengyang
 */
@Getter public abstract class BaseBullet extends AbstractFlyingObject {
    
    private final int power;
    
    protected BaseBullet(int power, BufferedImage image) {
        Args.assertPositive(power, "power");
        this.power = power;
        setImage(image);
    }
    
    @Override public void forward() {
        locationX += speedX;
        locationY += speedY;
        if (!getBoundingBox().intersects(Main.WINDOW_AREA)) {
            vanish();
        }
    }
}
