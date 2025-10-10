package edu.hitsz.util;

import pers.hpcx.util.Args;

/**
 * 定时触发器
 *
 * @author fengyang
 */
public class TimedTrigger {
    
    private final int duration;
    
    private int nextTriggerTime = 0;
    
    public TimedTrigger(int duration) {
        Args.assertNonNegative(duration, "duration");
        this.duration = duration;
    }
    
    public boolean isTriggered(int deltaTime) {
        if (nextTriggerTime <= 0) {
            nextTriggerTime = duration;
        }
        nextTriggerTime -= deltaTime;
        return nextTriggerTime <= 0;
    }
}
