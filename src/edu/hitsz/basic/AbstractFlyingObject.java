package edu.hitsz.basic;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 可飞行对象的父类
 *
 * @author hitsz
 * @author fengyang
 */
@Getter @Setter public abstract class AbstractFlyingObject {
    
    // locationX、locationY 为图片中心位置坐标
    protected int locationX;
    protected int locationY;
    
    // x 轴移动速度
    protected int speedX;
    
    // y 轴移动速度
    protected int speedY;
    
    // 图片，懒加载
    protected BufferedImage image = null;
    
    // 生存标记，标记为 false 的对象会在下次刷新时清除
    protected boolean alive = true;
    
    protected AbstractFlyingObject(int locationX, int locationY, int speedX, int speedY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
    }
    
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
    
    public Rectangle getBoundingBox() {
        return new Rectangle(locationX - getWidth() / 2, locationY - getHeight() / 2, getWidth(), getHeight());
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
