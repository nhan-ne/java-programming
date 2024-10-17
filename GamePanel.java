package com.example;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
    private static final int GRID_SIZE = 20; // Size of the grid
    private static final int CELL_SIZE = 30; // Size of each cell
    private final FoodImpl food;

    public GamePanel() {
        this.food = new FoodImpl();
        this.food.generatePosition(GRID_SIZE);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new java.awt.Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the grid
        g.setColor(Color.GRAY);
        for (int i = 0; i <= GRID_SIZE; i++) {
            // Draw vertical lines
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, GRID_SIZE * CELL_SIZE);
            // Draw horizontal lines
            g.drawLine(0, i * CELL_SIZE, GRID_SIZE * CELL_SIZE, i * CELL_SIZE);
        }

        // Render the food on top of the grid
        food.render(g, CELL_SIZE);
    }

    public void regenerateFood() {
        food.update(); // Update the food positions and selected items
        repaint();
    }

    public int getGridSize() {
        return GRID_SIZE;
    }

    public int getCellSize() {
        return CELL_SIZE;
    }
}
