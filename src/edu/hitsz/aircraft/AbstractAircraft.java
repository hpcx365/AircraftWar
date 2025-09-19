package edu.hitsz.aircraft;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.util.RandomTimedTrigger;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 * @author fengyang
 */
@Getter @Setter public abstract class AbstractAircraft extends AbstractFlyingObject {
    
    // 最大生命值
    protected int maxHealth;
    
    // 剩余生命值
    protected int health;
    
    // 子弹发射定时触发器，为 null 表示无法发射子弹
    protected RandomTimedTrigger shootTrigger;
    
    protected AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int health, RandomTimedTrigger shootTrigger) {
        super(locationX, locationY, speedX, speedY);
        this.health = health;
        this.maxHealth = health;
        this.shootTrigger = shootTrigger;
    }
    
    public void decreaseHp(int decrease) {
        health = Math.max(health - decrease, 0);
        if (health == 0) {
            vanish();
        }
    }
    
    public void increaseHp(int increase) {
        health = Math.min(health + increase, maxHealth);
    }
    
    /**
     * 飞机射击方法，可射击对象必须实现
     *
     * @return 可射击对象需实现，返回子弹
     * 非可射击对象空实现，返回null
     */
    public abstract List<BaseBullet> shoot();
}
