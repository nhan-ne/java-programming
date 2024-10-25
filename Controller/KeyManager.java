package resource.code.Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import resource.code.Model.SnakeManager;

public class KeyManager extends KeyAdapter {
    private final SnakeManager snakeManager;

    public KeyManager(SnakeManager snakeManager) {
        this.snakeManager = snakeManager;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                snakeManager.setDirection(SnakeManager.Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                snakeManager.setDirection(SnakeManager.Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                snakeManager.setDirection(SnakeManager.Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                snakeManager.setDirection(SnakeManager.Direction.RIGHT);
                break;
        }
    }
}