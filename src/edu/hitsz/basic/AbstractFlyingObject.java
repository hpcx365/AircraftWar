package edu.hitsz.basic;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * 可飞行对象的父类
 *
 * @author hitsz
 * @author fengyang
 */
@Getter @Setter public abstract class AbstractFlyingObject {
    
    // locationX、locationY 为图片中心位置坐标
    protected double locationX;
    protected double locationY;
    
    // x, y 轴移动速度
    protected double speedX;
    protected double speedY;
    
    // 图片，懒加载
    protected BufferedImage image = null;
    
    // 生存标记，标记为 false 的对象会在下次刷新时清除
    protected boolean alive = true;
    
    /**
     * 可飞行对象根据速度移动
     */
    public void forward() {
        locationX += speedX;
        locationY += speedY;
        if (locationX <= 10 || locationX >= Main.WINDOW_WIDTH - 10) {
            // 横向超出边界后反向
            speedX = -speedX;
        }
        if (!getBoundingBox().intersects(Main.WINDOW_AREA)) {
            vanish();
        }
    }
    
    public boolean crash(AbstractFlyingObject that) {
        return getBoundingBox().intersects(that.getBoundingBox());
    }
    
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(locationX - 0.5 * getWidth(), locationY - 0.5 * getHeight(), getWidth(), getHeight());
    }
    
    public BufferedImage getImage() {
        if (image == null) {
            image = ImageManager.get(this);
        }
        return image;
    }
    
    public int getWidth() {
        return getImage().getWidth();
    }
    
    public int getHeight() {
        return getImage().getHeight();
    }
    
    public void vanish() {
        alive = false;
    }
}
