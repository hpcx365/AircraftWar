package pers.hpcx.app;

import pers.hpcx.logger.Logger;
import pers.hpcx.logger.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 *
 * @author fengyang
 */
public class Main {
    
    public static final Logger LOGGER = LoggerFactory.newConsoleLogger();
    
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    
    private static final String CARD_START = "start";
    private static final String CARD_GAME = "game";
    private static final String CARD_END = "end";
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartPanel startPanel = new StartPanel();
            GamePanel gamePanel = new GamePanel();
            EndPanel endPanel = new EndPanel();
            
            JPanel stage = new JPanel();
            stage.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
            
            CardLayout layout = new CardLayout();
            stage.setLayout(layout);
            stage.add(startPanel, CARD_START);
            stage.add(gamePanel, CARD_GAME);
            stage.add(endPanel, CARD_END);
            
            JFrame frame = new JFrame("Aircraft War");
            frame.setContentPane(stage);
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            startPanel.setStartGameAction(() -> {
                gamePanel.start(startPanel);
                layout.show(stage, CARD_GAME);
            });
            
            gamePanel.setGameOverAction(() -> {
                endPanel.refresh(gamePanel);
                layout.show(stage, CARD_END);
            });
            
            endPanel.setReplayAction(() -> {
                layout.show(stage, CARD_START);
            });
            
            frame.setVisible(true);
        });
    }
}
