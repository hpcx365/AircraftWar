package pers.hpcx.score;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public interface ScoreboardDAO {
    
    List<ScoreboardEntry> listAll();
    
    void writeAll(List<ScoreboardEntry> entries);
    
    default void append(ScoreboardEntry entry) {
        List<ScoreboardEntry> list = new ArrayList<>(listAll());
        list.add(entry);
        writeAll(list);
    }
    
    default void dropAll(int[] indices) {
        List<ScoreboardEntry> list = new ArrayList<>(listAll());
        int[] sorted = IntStream.of(indices).distinct().sorted().toArray();
        for (int i = sorted.length - 1; i >= 0; i--) {
            list.remove(sorted[i]);
        }
        writeAll(list);
    }
}
