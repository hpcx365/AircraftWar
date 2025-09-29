package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HeroAircraftTest {
    
    private HeroAircraft heroAircraft;
    
    @BeforeEach
    void setUp() {
        // 重置单例实例以确保测试独立性
        HeroAircraft.resetInstanceForTest();
        heroAircraft = HeroAircraft.getInstance();
    }
    
    /**
     * 测试英雄机单例模式实现
     * 黑盒测试：验证getInstance方法返回相同实例
     * 白盒测试：验证双重检查锁定机制
     */
    @Test
    void testSingletonInstance() {
        // 黑盒测试：多次调用getInstance应该返回相同实例
        HeroAircraft instance1 = HeroAircraft.getInstance();
        HeroAircraft instance2 = HeroAircraft.getInstance();
        
        assertSame(instance1, instance2, "多次调用getInstance()应该返回相同实例");
        
        // 验证初始属性
        assertEquals(Main.WINDOW_WIDTH / 2, instance1.getLocationX(), "英雄机初始X坐标应为窗口宽度的一半");
        assertEquals(1000, instance1.getHealth(), "英雄机初始生命值应为1000");
        assertEquals(20, instance1.getPower(), "英雄机初始子弹伤害应为20");
        assertEquals(1, instance1.getShootNum(), "英雄机初始射击数量应为1");
    }
    
    /**
     * 测试英雄机射击功能
     * 黑盒测试：验证射击方法返回正确数量的子弹
     * 白盒测试：验证子弹生成逻辑
     */
    @Test
    void testShoot() {
        // 设置射击参数
        heroAircraft.setShootNum(3);
        heroAircraft.setPower(30);
        
        // 调用射击方法
        List<BaseBullet> bullets = heroAircraft.shoot();
        
        // 验证返回的子弹数量
        assertEquals(3, bullets.size(), "应该生成3颗子弹");
        
        // 验证子弹属性
        for (int i = 0; i < bullets.size(); i++) {
            BaseBullet bullet = bullets.get(i);
            assertNotNull(bullet, "子弹不应为null");
            assertEquals(30, bullet.getPower(), "子弹伤害应为30");
            
            // 验证子弹位置（基于英雄机位置计算）
            int expectedX = heroAircraft.getLocationX() + (i * 2 - 3 + 1) * 10;
            assertEquals(expectedX, bullet.getLocationX(), "子弹X坐标计算应正确");
            assertEquals(heroAircraft.getLocationY() - 2, bullet.getLocationY(), "子弹Y坐标应为英雄机Y坐标减2");
        }
    }
    
    /**
     * 测试英雄机减少生命值功能
     * 黑盒测试：验证生命值减少和边界条件
     * 白盒测试：验证decreaseHp方法中的vanish调用条件
     */
    @Test
    void testDecreaseHp() {
        int initialHealth = heroAircraft.getHealth();
        
        // 正常减少生命值
        heroAircraft.decreaseHp(100);
        assertEquals(initialHealth - 100, heroAircraft.getHealth(), "生命值应减少100");
        
        // 测试减少超过剩余生命值的情况
        heroAircraft.decreaseHp(2000);
        assertEquals(0, heroAircraft.getHealth(), "生命值不应低于0");
        assertFalse(heroAircraft.isAlive(), "生命值为0时英雄机应标记为死亡");
    }
    
    /**
     * 测试英雄机增加生命值功能
     * 黑盒测试：验证生命值增加和上限控制
     * 白盒测试：验证increaseHp方法中的最大值限制
     */
    @Test
    void testIncreaseHp() {
        // 先减少生命值
        heroAircraft.decreaseHp(500);
        int currentHealth = heroAircraft.getHealth();
        
        // 增加生命值
        heroAircraft.increaseHp(300);
        assertEquals(currentHealth + 300, heroAircraft.getHealth(), "生命值应增加300");
        
        // 测试超过最大生命值的情况
        heroAircraft.increaseHp(1000);
        assertEquals(1000, heroAircraft.getHealth(), "生命值不应超过最大值1000");
    }
    
    /**
     * 测试英雄机forward方法抛出异常
     * 黑盒测试：验证不支持的操作
     * 白盒测试：验证方法中的异常抛出逻辑
     */
    @Test
    void testForwardThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            heroAircraft.forward();
        }, "调用forward方法应抛出UnsupportedOperationException");
    }
}
