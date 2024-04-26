package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame implements Runnable{
     GamePanel gamePanel;
    int width = 700;
    int height = 700;
    int targetWidth = 200;
    int targetHeight = 200;
    int fps = 60; // Adjust as needed
    int animationDuration = 3000;

    public GameFrame() {

        gamePanel = new GamePanel();


        this.setTitle("Window Kill");
        this.add(gamePanel);
        this.setSize(width, height);
        this.setUndecorated(true);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        new Thread(this).start();


    }

    @Override
    public void run() {
        int totalSteps = fps * (animationDuration / 1000); // Total number of steps
        int initialX = getX(); // Initial X position
        int initialY = getY(); // Initial Y position
        int initialWidth = getWidth(); // Initial width
        int initialHeight = getHeight(); // Initial height

        int stepWidth = (initialWidth - targetWidth) / totalSteps;
        int stepHeight = (initialHeight - targetHeight) / totalSteps;

        while (getWidth() > targetWidth && getHeight() > targetHeight) {
            // Gradually decrease the frame size
            setSize(Math.max(getWidth() - stepWidth, targetWidth), Math.max(getHeight() - stepHeight, targetHeight));

            // Adjust frame position to keep it centered
            int dx = (initialWidth - getWidth()) / 2;
            int dy = (initialHeight - getHeight()) / 2;
            setLocation(initialX + dx, initialY + dy);

            // Repaint the game panel
            gamePanel.repaint();

            // Sleep to control the FPS
            try {
                Thread.sleep(1000 / fps);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    }



