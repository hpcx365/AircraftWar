package pers.hpcx.basic;

import lombok.Getter;
import lombok.Setter;
import pers.hpcx.app.Main;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * 可飞行对象的父类
 *
 * @author fengyang
 */
@Getter @Setter public abstract class AbstractFlyingObject {
    
    // locationX、locationY 为图片中心位置坐标
    protected double locationX;
    protected double locationY;
    
    // x, y 轴移动速度
    protected double speedX;
    protected double speedY;
    
    // 图片
    protected final BufferedImage image;
    
    // 生存标记，标记为 false 的对象会在下次刷新时清除
    protected boolean alive = true;
    
    protected AbstractFlyingObject(BufferedImage image) {
        this.image = image;
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
    }
    
    public abstract void onBombExplosion();
    
    public boolean isInvalid() {
        return !(isAlive() && isInScreen());
    }
    
    public boolean isInScreen() {
        return getCollisionBox().intersects(-Main.WINDOW_WIDTH, -Main.WINDOW_HEIGHT, 3 * Main.WINDOW_WIDTH, 3 * Main.WINDOW_HEIGHT);
    }
    
    public double getCollisionWidth() {
        return image.getWidth();
    }
    
    public double getCollisionHeight() {
        return image.getHeight();
    }
    
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(locationX - 0.5 * getCollisionWidth(), locationY - 0.5 * getCollisionHeight(), getCollisionWidth(), getCollisionHeight());
    }
    
    public boolean crash(AbstractFlyingObject that) {
        return getCollisionBox().intersects(that.getCollisionBox());
    }
}
