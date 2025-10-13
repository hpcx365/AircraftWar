package pers.hpcx.buff;

import lombok.Data;
import pers.hpcx.app.GamePanel;

@Data public abstract class BaseBuff {
    
    /**
     * 若大于 0 则表示剩余持续时间<br>
     * 若等于 0 则表示已过期<br>
     * 若小于 0 则表示无限持续时间<br>
     */
    protected int expirationTime;
    
    protected BaseBuff(int expirationTime) {
        this.expirationTime = expirationTime;
    }
    
    public void update(int deltaTime, GamePanel gamePanel) {
        if (expirationTime > 0) {
            expirationTime = Math.max(expirationTime - deltaTime, 0);
        }
    }
    
    public boolean isValid() {
        return expirationTime != 0;
    }
    
    @Override public final int hashCode() {
        return getClass().hashCode();
    }
    
    @Override public final boolean equals(Object o) {
        return o != null && (o == this || getClass().equals(o.getClass())); // buff 按类型比较
    }
}
