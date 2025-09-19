package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 *
 * @author hitsz
 * @author fengyang
 */
public class Main {
    
    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Rectangle WINDOW_AREA = new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    
    public static void main(String[] args) {
        Game game = new Game();
        game.setFocusable(true);
        game.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        
        JFrame frame = new JFrame("Aircraft War");
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);
        game.start();
    }
}
