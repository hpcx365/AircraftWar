package pers.hpcx.score;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ScoreboardEntry(
    String player,
    int score,
    int aliveTime,
    LocalDateTime recordTime
) implements Comparable<ScoreboardEntry>, Serializable {
    
    @Override public int compareTo(@NotNull ScoreboardEntry o) {
        return Integer.compare(o.score, score); // 倒序排序
    }
}
