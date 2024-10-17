package com.example;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class SnakeGame extends JFrame {
    private GamePanel gamePanel;
    private final Timer timer;

    public SnakeGame() {
        setTitle("Snake Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        gamePanel = new GamePanel();
        add(gamePanel);
        pack();

        // Set window size based on grid size and cell size
        int windowSize = gamePanel.getGridSize() * gamePanel.getCellSize();
        setSize(windowSize + 16, windowSize + 39); // Add window border size

        setLocationRelativeTo(null);
        setVisible(true);

        // Timer for regenerating food every 10 seconds
        timer = new Timer(3000, (ActionEvent e) -> {
            gamePanel.regenerateFood();
        });
        timer.start();
    }
}
