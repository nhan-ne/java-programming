// File: App.java
package com.example;

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SnakeGame::new);
    }
}