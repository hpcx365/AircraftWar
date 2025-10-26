package pers.hpcx.app;

import lombok.Getter;
import lombok.Setter;
import pers.hpcx.template.Difficulty;
import pers.hpcx.visual.SpringGridLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static pers.hpcx.visual.SpringGridLayout.Spring;

@Getter @Setter public class StartPanel extends JPanel {
    
    private final JLabel selectDifficultyLabel = new JLabel("请选择难度");
    private final JButton easyModeButton = new JButton(Difficulty.EASY.getDescription() + "模式");
    private final JButton normalModeButton = new JButton(Difficulty.NORMAL.getDescription() + "模式");
    private final JButton difficultModeButton = new JButton(Difficulty.HARD.getDescription() + "模式");
    private final JCheckBox silentModeCheckBox = new JCheckBox("静音模式", false);
    
    private Difficulty difficulty;
    private Runnable startGameAction;
    
    public StartPanel() {
        init();
    }
    
    private void init() {
        SpringGridLayout layout = new SpringGridLayout();
        
        layout.getXSprings().add(Spring.elastic(100));
        layout.getXSprings().add(Spring.elastic(200));
        layout.getXSprings().add(Spring.elastic(100));
        
        layout.getYSprings().add(Spring.elastic(300));
        layout.getYSprings().add(Spring.elastic(200));
        layout.getYSprings().add(Spring.elastic(100));
        layout.getYSprings().add(Spring.elastic(200));
        layout.getYSprings().add(Spring.elastic(100));
        layout.getYSprings().add(Spring.elastic(200));
        layout.getYSprings().add(Spring.elastic(100));
        layout.getYSprings().add(Spring.elastic(200));
        layout.getYSprings().add(Spring.elastic(200));
        layout.getYSprings().add(Spring.elastic(200));
        layout.getYSprings().add(Spring.elastic(300));
        
        setLayout(layout);
        add(selectDifficultyLabel, "1, 1");
        add(easyModeButton, "1, 3");
        add(normalModeButton, "1, 5");
        add(difficultModeButton, "1, 7");
        add(silentModeCheckBox, "1, 9");
        
        selectDifficultyLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        easyModeButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
        normalModeButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
        difficultModeButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
        silentModeCheckBox.setFont(new Font("Microsoft YaHei", Font.PLAIN, 20));
        
        selectDifficultyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        silentModeCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        
        ActionListener listener = e -> {
            if (e.getSource() == easyModeButton) {
                difficulty = Difficulty.EASY;
            } else if (e.getSource() == normalModeButton) {
                difficulty = Difficulty.NORMAL;
            } else if (e.getSource() == difficultModeButton) {
                difficulty = Difficulty.HARD;
            }
            if (startGameAction != null) {
                startGameAction.run();
            }
        };
        easyModeButton.addActionListener(listener);
        normalModeButton.addActionListener(listener);
        difficultModeButton.addActionListener(listener);
    }
}
