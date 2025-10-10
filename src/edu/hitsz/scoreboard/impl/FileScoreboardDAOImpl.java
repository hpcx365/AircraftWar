package edu.hitsz.scoreboard.impl;

import edu.hitsz.scoreboard.ScoreboardDAO;
import edu.hitsz.scoreboard.ScoreboardEntry;
import lombok.SneakyThrows;
import lombok.Value;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

@Value public class FileScoreboardDAOImpl implements ScoreboardDAO {
    
    Path savePath;
    
    @SneakyThrows @Override public void append(ScoreboardEntry entry) {
        var list = listAll();
        list.add(entry);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(savePath))) {
            oos.writeObject(list);
        }
    }
    
    @SuppressWarnings("unchecked") @SneakyThrows
    @Override public ArrayList<ScoreboardEntry> listAll() {
        if (Files.notExists(savePath)) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(savePath))) {
            return (ArrayList<ScoreboardEntry>) ois.readObject();
        }
    }
}
