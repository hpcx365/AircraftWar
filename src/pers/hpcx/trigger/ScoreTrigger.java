package pers.hpcx.trigger;

/**
 * 计分触发器
 *
 * @author fengyang
 */
public class ScoreTrigger {
    
    private int scoreGap;
    private int nextTriggerScore;
    
    public void reset(int scoreGap) {
        this.scoreGap = scoreGap;
        this.nextTriggerScore = -1;
    }
    
    public boolean isTriggered(int currentScore) {
        if (nextTriggerScore < 0) {
            nextTriggerScore = currentScore + scoreGap;
            return false;
        }
        if (currentScore >= nextTriggerScore) {
            nextTriggerScore += scoreGap;
            return true;
        }
        return false;
    }
}
