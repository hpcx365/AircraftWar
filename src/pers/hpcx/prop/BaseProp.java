package pers.hpcx.prop;

import pers.hpcx.app.GamePanel;
import pers.hpcx.basic.AbstractFlyingObject;

import java.awt.image.BufferedImage;

/**
 * 道具基类<br>
 * 可以实现不同类型的道具
 *
 * @author fengyang
 */
public abstract class BaseProp extends AbstractFlyingObject {
    
    protected BaseProp(BufferedImage image) {
        setImage(image);
    }
    
    public abstract void takeEffect(GamePanel gamePanel);
}
