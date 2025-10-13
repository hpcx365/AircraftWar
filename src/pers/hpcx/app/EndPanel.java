package pers.hpcx.app;

import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import pers.hpcx.score.ScoreboardDAO;
import pers.hpcx.score.ScoreboardEntry;
import pers.hpcx.score.impl.FileScoreboardDAOImpl;
import pers.hpcx.visual.SpringGridLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static pers.hpcx.visual.SpringGridLayout.Spring;

public class EndPanel extends JPanel {
    
    private final JLabel difficultyLabel = new JLabel();
    private final JLabel scoreboardLabel = new JLabel("排行榜");
    private final JScrollPane scoreboardScrollPane = new JScrollPane();
    private final JTable scoreboardTable = new JTable();
    private final DefaultTableModel scoreboardTableModel = new DefaultTableModel() {
        
        @Override public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JButton deleteEntryButton = new JButton("删除选中记录");
    private final JButton replayButton = new JButton("重新开始");
    
    // 计分板数据访问对象
    private final ScoreboardDAO scoreboard = new FileScoreboardDAOImpl(Paths.get("scoreboard"));
    
    @Setter private Runnable replayAction;
    
    public EndPanel() {
        init();
    }
    
    public void refresh(GamePanel game) {
        difficultyLabel.setText("难度：" + game.getDifficulty().getDescription());
        
        LocalDateTime now = LocalDateTime.now();
        String player = fetchUserName();
        if (player != null) {
            scoreboard.append(new ScoreboardEntry(player, game.getScore(), game.getTime(), now));
        }
        refreshTable();
    }
    
    private @Nullable String fetchUserName() {
        for (; ; ) {
            String input = JOptionPane.showInputDialog(this, "请输入你的名字以记录成绩：", "Game Over", JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                JOptionPane.showMessageDialog(this, "未输入名字，本次游玩不计入成绩");
                return null;
            }
            if (input.isBlank()) {
                JOptionPane.showMessageDialog(this, "名字不能为空！");
                continue;
            }
            return input;
        }
    }
    
    private void refreshTable() {
        List<ScoreboardEntry> entries = new ArrayList<>(scoreboard.listAll());
        entries.sort(null);
        scoreboard.writeAll(entries);
        
        for (int i = scoreboardTableModel.getRowCount() - 1; i >= 0; i--) {
            scoreboardTableModel.removeRow(i);
        }
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd hh:mm::ss");
        for (int i = 0; i < entries.size(); i++) {
            ScoreboardEntry entry = entries.get(i);
            scoreboardTableModel.addRow(new Object[] {
                i + 1,
                entry.player(),
                entry.score(),
                "%02d:%02d.%03d".formatted(entry.aliveTime() / 1000 / 60, entry.aliveTime() / 1000 % 60, entry.aliveTime() % 1000),
                formatter.format(entry.recordTime())
            });
        }
    }
    
    private void init() {
        SpringGridLayout layout = new SpringGridLayout();
        
        layout.getXSprings().add(Spring.fixed(10));
        layout.getXSprings().add(Spring.elastic(100));
        layout.getXSprings().add(Spring.fixed(10));
        
        layout.getYSprings().add(Spring.fixed(20));
        layout.getYSprings().add(Spring.fixed(40));
        layout.getYSprings().add(Spring.fixed(10));
        layout.getYSprings().add(Spring.fixed(40));
        layout.getYSprings().add(Spring.fixed(10));
        layout.getYSprings().add(Spring.elastic(100));
        layout.getYSprings().add(Spring.fixed(10));
        layout.getYSprings().add(Spring.fixed(50));
        layout.getYSprings().add(Spring.fixed(10));
        layout.getYSprings().add(Spring.fixed(50));
        layout.getYSprings().add(Spring.fixed(20));
        
        setLayout(layout);
        add(difficultyLabel, "1, 1");
        add(scoreboardLabel, "1, 3");
        add(scoreboardScrollPane, "1, 5");
        add(deleteEntryButton, "1, 7");
        add(replayButton, "1, 9");
        
        deleteEntryButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, "确定要删除选定记录吗？", "确认", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
            int[] selectedRows = scoreboardTable.getSelectedRows();
            for (int i = 0; i < selectedRows.length; i++) {
                selectedRows[i] = scoreboardTable.convertRowIndexToModel(selectedRows[i]);
            }
            scoreboard.dropAll(selectedRows);
            refreshTable();
        });
        
        replayButton.addActionListener(e -> {
            if (replayAction != null) {
                replayAction.run();
            }
        });
        
        scoreboardScrollPane.setViewportView(scoreboardTable);
        
        scoreboardTableModel.setColumnIdentifiers(new String[] {"排名", "玩家", "得分", "存活时间", "记录时间"});
        scoreboardTable.setModel(scoreboardTableModel);
        scoreboardTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        difficultyLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        scoreboardLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
        scoreboardScrollPane.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
        deleteEntryButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        replayButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        
        scoreboardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreboardLabel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 50), 2, true));
        scoreboardLabel.setForeground(new Color(134, 58, 11));
        
        // 设置表头字体
        JTableHeader header = scoreboardTable.getTableHeader();
        header.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
    }
}
