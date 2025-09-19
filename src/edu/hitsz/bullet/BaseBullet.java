package edu.hitsz.bullet;

import edu.hitsz.basic.AbstractFlyingObject;
import lombok.Getter;

/**
 * 子弹基类<br>
 * 可以实现不同类型的子弹
 *
 * @author hitsz
 * @author fengyang
 */
@Getter public abstract class BaseBullet extends AbstractFlyingObject {
    
    protected final int power;
    
    protected BaseBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY);
        this.power = power;
    }
}
