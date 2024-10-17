package com.example;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

public final class FoodImpl implements Food {
    private final int[] x = new int[8]; // Store x-coordinates for 8 food items
    private final int[] y = new int[8]; // Store y-coordinates for 8 food items
    private final BufferedImage[] images = new BufferedImage[8]; // Store images
    private final String[] imagePaths = {
        "/image/Pineapple.png",
        "/image/Watermelon.png",
        "/image/Apple.png",
        "/image/Banana.png",
        "/image/Orange.png",
        "/image/Cherry.png",
        "/image/Mulberry.png",
        "/image/Lemon.png"
    };

    private final int[] selectedFood = new int[3]; // Store indices of 3 randomly selected food items

    public FoodImpl() {
        loadImages();
        generatePosition(20); // Generate positions for all 8 food items
        selectFood(); // Randomly select 3 food items
    }

    private void loadImages() {
        for (int i = 0; i < images.length; i++) {
            try {
                images[i] = ImageIO.read(getClass().getResource(imagePaths[i]));
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Cannot load food image from path: " + imagePaths[i]);
                e.printStackTrace();
                images[i] = null; // Set image to null if it can't be loaded
            }
        }
    }

    @Override
    public void generatePosition(int gridSize) {
        Random rand = new Random();
        Set<Integer> usedPositions = new HashSet<>();
        for (int i = 0; i < x.length; i++) {
            do {
                x[i] = rand.nextInt(gridSize);
                y[i] = rand.nextInt(gridSize);
            } while (usedPositions.contains(x[i] + y[i] * gridSize)); // Check for duplicates
            usedPositions.add(x[i] + y[i] * gridSize); // Store used position
        }
    }

    private void selectFood() {
        Random rand = new Random();
        Set<Integer> selectedIndices = new HashSet<>();
        while (selectedIndices.size() < selectedFood.length) {
            int index = rand.nextInt(8); // Randomly select food item index
            selectedIndices.add(index);
        }
        int i = 0;
        for (Integer index : selectedIndices) {
            selectedFood[i++] = index; // Store index of selected food
        }
    }

    @Override
    public void update() {
        generatePosition(20); // Update positions
        selectFood(); // Update the selected food items
    }

    @Override
    public int[][] getPosition() {
        int[][] positions = new int[3][2]; // Store positions of 3 selected food items
        for (int i = 0; i < selectedFood.length; i++) {
            positions[i][0] = x[selectedFood[i]];
            positions[i][1] = y[selectedFood[i]];
        }
        return positions;
    }

    @Override
    public void render(Graphics graphics, int cellSize) {
        for (int i = 0; i < selectedFood.length; i++) {
            int index = selectedFood[i];
            if (images[index] != null) {
                graphics.drawImage(images[index], x[index] * cellSize, y[index] * cellSize, cellSize, cellSize, null);
            } else {
                graphics.setColor(java.awt.Color.RED);
                graphics.fillOval(x[index] * cellSize, y[index] * cellSize, cellSize, cellSize); // Fallback when image is missing
            }
        }
    }
}
