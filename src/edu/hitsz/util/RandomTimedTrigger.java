package edu.hitsz.util;

import pers.hpcx.util.Args;
import pers.hpcx.util.Random;

/**
 * 随机定时触发器<br>
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
            nextTriggerTime = Random.getInstance().nextRange(minDuration, maxDuration);
        }
        nextTriggerTime -= deltaTime;
        return nextTriggerTime <= 0;
    }
}
