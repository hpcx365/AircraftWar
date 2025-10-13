package pers.hpcx.score.impl;

import lombok.SneakyThrows;
import lombok.Value;
import pers.hpcx.score.ScoreboardDAO;
import pers.hpcx.score.ScoreboardEntry;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Value public class FileScoreboardDAOImpl implements ScoreboardDAO {
    
    Path savePath;
    
    @SuppressWarnings("unchecked") @SneakyThrows
    @Override public List<ScoreboardEntry> listAll() {
        if (Files.notExists(savePath)) {
            return List.of();
        }
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(savePath))) {
            return (List<ScoreboardEntry>) ois.readObject();
        }
    }
    
    @SneakyThrows @Override public void writeAll(List<ScoreboardEntry> entries) {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(savePath))) {
            oos.writeObject(entries);
        }
    }
}
