package pers.hpcx.trigger;

/**
 * 定时触发器
 *
 * @author fengyang
 */
public class TimedTrigger {
    
    private long interval;
    private long nextTriggerTime;
    
    public TimedTrigger() {
    }
    
    public TimedTrigger(long interval) {
        reset(interval);
    }
    
    public void reset(long interval) {
        this.interval = interval;
        this.nextTriggerTime = -1;
    }
    
    public boolean isTriggered(long currentTime) {
        if (nextTriggerTime < 0) {
            nextTriggerTime = currentTime + interval;
            return false;
        }
        if (currentTime >= nextTriggerTime) {
            nextTriggerTime += interval;
            return true;
        }
        return false;
    }
}
