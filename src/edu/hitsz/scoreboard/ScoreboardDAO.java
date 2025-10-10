package edu.hitsz.scoreboard;

import java.util.List;

public interface ScoreboardDAO {
    
    void append(ScoreboardEntry entry);
    
    List<ScoreboardEntry> listAll();
}
