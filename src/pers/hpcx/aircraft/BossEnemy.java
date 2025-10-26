package pers.hpcx.aircraft;

import lombok.Getter;
import lombok.Setter;
import pers.hpcx.app.Main;
import pers.hpcx.app.ResourceManager;
import pers.hpcx.bullet.BaseBullet;

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
        super(health, ResourceManager.BOSS_ENEMY_IMAGE);
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
    
    @Override public void onBombExplosion() {
        decreaseHp(100);
    }
    
    @Override public List<? extends BaseBullet> shoot() {
        return hovering ? super.shoot() : List.of();
    }
}
