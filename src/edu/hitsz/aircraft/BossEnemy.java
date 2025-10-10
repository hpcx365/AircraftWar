package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Boss敌机
 * 可环形射击，悬浮
 *
 * @author fengyang
 */
@Getter @Setter public class BossEnemy extends AbstractAircraft {
    
    // 是否正在悬浮射击
    private boolean hovering = false;
    
    public BossEnemy(int health) {
        super(health);
    }
    
    @Override public void forward() {
        if (!hovering) {
            if (getLocationY() < Main.WINDOW_HEIGHT * 0.2) {
                speedX = 0;
                speedY = 1;
            } else {
                hovering = true;
                speedX = 1;
                speedY = 0;
            }
        }
        super.forward();
    }
    
    @Override public List<? extends BaseBullet> shoot() {
        return hovering ? super.shoot() : List.of();
    }
}
