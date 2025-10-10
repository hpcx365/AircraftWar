package edu.hitsz.scoreboard;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ScoreboardEntry(
    String playerName,
    int score,
    int survivalTime,
    LocalDateTime playTime
) implements Comparable<ScoreboardEntry>, Serializable {
    
    @Override public int compareTo(@NotNull ScoreboardEntry o) {
        return Integer.compare(o.score, score); // 倒序排序
    }
}
