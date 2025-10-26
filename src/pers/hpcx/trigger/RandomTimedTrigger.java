package pers.hpcx.trigger;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

/**
 * 随机定时触发器
 *
 * @author fengyang
 */
@Getter @Setter public class RandomTimedTrigger {
    
    private final double standardDeviation;
    
    private double meanInterval;
    private long nextTriggerTime;
    
    public RandomTimedTrigger(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
    
    public void reset(double meanInterval) {
        this.meanInterval = meanInterval;
        this.nextTriggerTime = -1;
    }
    
    public boolean isTriggered(long currentTime, Random random) {
        if (nextTriggerTime < 0) {
            nextTriggerTime = currentTime + randomInterval(random);
            return false;
        }
        if (currentTime >= nextTriggerTime) {
            nextTriggerTime += randomInterval(random);
            return true;
        }
        return false;
    }
    
    private long randomInterval(Random random) {
        return Math.max(Math.round(random.nextGaussian(meanInterval, standardDeviation)), 0);
    }
}
