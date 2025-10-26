package pers.hpcx.app;

import pers.hpcx.aircraft.HeroAircraft;
import pers.hpcx.util.Ranges;

import java.awt.event.*;

import static java.awt.event.KeyEvent.*;

/**
 * 英雄机控制类
 * 监听鼠标和键盘，控制英雄机的移动
 *
 * @author fengyang
 */
public class HeroController implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
    
    private int directionX = 0;
    private int directionY = 0;
    private boolean dragging = false;
    
    private double heroLocationX;
    private double heroLocationY;
    
    private GamePanel gamePanel;
    
    public void install(GamePanel gamePanel) {
        uninstall();
        this.gamePanel = gamePanel;
        this.gamePanel.addMouseListener(this);
        this.gamePanel.addMouseMotionListener(this);
        this.gamePanel.addMouseWheelListener(this);
        this.gamePanel.addKeyListener(this);
    }
    
    public void uninstall() {
        if (gamePanel == null) {
            return;
        }
        
        gamePanel.removeMouseListener(this);
        gamePanel.removeMouseMotionListener(this);
        gamePanel.removeMouseWheelListener(this);
        gamePanel.removeKeyListener(this);
        gamePanel = null;
        
        directionX = 0;
        directionY = 0;
        dragging = false;
        heroLocationX = 0;
        heroLocationY = 0;
    }
    
    public void move() {
        HeroAircraft hero = gamePanel.getHeroAircraft();
        
        if (!dragging) {
            heroLocationX = hero.getLocationX() + directionX * 8;
            heroLocationY = hero.getLocationY() + directionY * 8;
        }
        
        heroLocationX = Ranges.limit(heroLocationX, 10, Main.WINDOW_WIDTH - 10);
        heroLocationY = Ranges.limit(heroLocationY, 10, Main.WINDOW_HEIGHT - 10);
        
        hero.setLocationX(heroLocationX);
        hero.setLocationY(heroLocationY);
    }
    
    @Override public void mousePressed(MouseEvent e) {
        if (gamePanel.getHeroAircraft().getCollisionBox().contains(e.getPoint())) {
            dragging = true;
        }
    }
    
    @Override public void mouseReleased(MouseEvent e) {
        dragging = false;
    }
    
    @Override public void mouseDragged(MouseEvent e) {
        if (dragging && directionX == 0 && directionY == 0) {
            heroLocationX = e.getX();
            heroLocationY = e.getY();
        }
    }
    
    @Override public void keyPressed(KeyEvent e) {
        if (dragging) {
            return;
        }
        switch (e.getKeyCode()) {
        case VK_LEFT, VK_A -> directionX = -1;
        case VK_RIGHT, VK_D -> directionX = 1;
        case VK_UP, VK_W -> directionY = -1;
        case VK_DOWN, VK_S -> directionY = 1;
        }
    }
    
    @Override public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case VK_LEFT, VK_RIGHT, VK_A, VK_D -> directionX = 0;
        case VK_UP, VK_DOWN, VK_W, VK_S -> directionY = 0;
        }
    }
    
    @Override public void mouseMoved(MouseEvent e) {
    }
    
    @Override public void keyTyped(KeyEvent e) {
    }
    
    @Override public void mouseClicked(MouseEvent e) {
    }
    
    @Override public void mouseEntered(MouseEvent e) {
    }
    
    @Override public void mouseExited(MouseEvent e) {
    }
    
    @Override public void mouseWheelMoved(MouseWheelEvent e) {
    }
}
