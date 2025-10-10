package edu.hitsz.util;

import pers.hpcx.util.Args;

/**
 * 计分触发器
 *
 * @author fengyang
 */
public class ScoreTrigger {
    
    private final int scoresPerTrigger;
    
    private int nextTriggerScore = 0;
    
    public ScoreTrigger(int scoresPerTrigger) {
        Args.assertNonNegative(scoresPerTrigger, "scoresPerTrigger");
        this.scoresPerTrigger = scoresPerTrigger;
    }
    
    public boolean isTriggered(int currentScore) {
        if (nextTriggerScore <= 0) {
            nextTriggerScore = scoresPerTrigger;
        }
        if (currentScore >= nextTriggerScore) {
            nextTriggerScore += scoresPerTrigger;
            return true;
        }
        return false;
    }
}
