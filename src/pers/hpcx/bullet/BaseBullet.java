package pers.hpcx.bullet;

import lombok.Getter;
import pers.hpcx.basic.AbstractFlyingObject;

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
        super(image);
        this.power = power;
    }
    
    @Override public void forward() {
        locationX += speedX;
        locationY += speedY;
    }
}
