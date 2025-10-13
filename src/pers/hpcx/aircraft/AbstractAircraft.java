package pers.hpcx.aircraft;

import lombok.Getter;
import lombok.Setter;
import pers.hpcx.basic.AbstractFlyingObject;
import pers.hpcx.bullet.BaseBullet;
import pers.hpcx.shoot.ShootingStrategy;
import pers.hpcx.trigger.TimedTrigger;
import pers.hpcx.util.Args;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author fengyang
 */
@Getter @Setter public abstract class AbstractAircraft extends AbstractFlyingObject {
    
    // 最大生命值
    protected final int maxHealth;
    
    // 剩余生命值
    protected int health;
    
    // 子弹发射定时触发器
    protected TimedTrigger shootingTrigger;
    
    // 射击策略
    protected ShootingStrategy<?> shootingStrategy;
    
    protected AbstractAircraft(int health) {
        Args.assertPositive(health, "health");
        this.health = health;
        this.maxHealth = health;
    }
    
    /**
     * 发射子弹
     */
    public List<? extends BaseBullet> shoot() {
        return shootingStrategy.shoot(this);
    }
    
    public void decreaseHp(int decrease) {
        Args.assertNonNegative(decrease, "decrease");
        health = Math.max(health - decrease, 0);
        if (health == 0) {
            vanish();
        }
    }
    
    public void increaseHp(int increase) {
        Args.assertNonNegative(increase, "increase");
        health = Math.min(health + increase, maxHealth);
    }
}
