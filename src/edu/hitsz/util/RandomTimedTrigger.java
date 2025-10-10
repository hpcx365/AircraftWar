package edu.hitsz.util;

import pers.hpcx.util.Args;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机定时触发器
 *
 * @author fengyang
 */
public class RandomTimedTrigger {
    
    private final int minDuration;
    private final int maxDuration;
    
    private int nextTriggerTime = 0;
    
    public RandomTimedTrigger(int minDuration, int maxDuration) {
        Args.assertNonNegative(minDuration, "minDuration");
        Args.assertLessOrEqual(minDuration, maxDuration, "minDuration", "maxDuration");
        this.minDuration = minDuration;
        this.maxDuration = maxDuration;
    }
    
    public boolean isTriggered(int deltaTime) {
        if (nextTriggerTime <= 0) {
            nextTriggerTime = ThreadLocalRandom.current().nextInt(minDuration, maxDuration);
        }
        nextTriggerTime -= deltaTime;
        return nextTriggerTime <= 0;
    }
}
