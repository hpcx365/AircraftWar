package pers.hpcx.aircraft;

import lombok.Getter;
import pers.hpcx.buff.BaseBuff;

import java.util.HashSet;
import java.util.Set;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author fengyang
 */
public class HeroAircraft extends AbstractAircraft {
    
    @Getter private final Set<BaseBuff> buffs = new HashSet<>();
    
    public HeroAircraft(int health) {
        super(health);
    }
    
    public void addBuff(BaseBuff buff) {
        buffs.remove(buff); // 移除同类型 buff 以刷新倒计时
        buffs.add(buff);
    }
    
    @Override public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
        throw new UnsupportedOperationException();
    }
}
