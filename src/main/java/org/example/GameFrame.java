package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame implements Runnable{
     GamePanel gamePanel;
    int width = 700;
    int height = 700;
    int targetWidth = 200;
    int targetHeight = 200;
    int fps = 60;
    int animationDuration = 4000;
    double lastTime;
    double endTime;
    double timeLeft;
    Thread gameThread;
    boolean isShrinking = false;

    public GameFrame() {

        gamePanel = new GamePanel(this);
        gameThread = new Thread(this);
        gameThread.start();
        this.setTitle("Window Kill");
        this.add(gamePanel);
       this.setBounds(0,0,width,height);
        this.setUndecorated(true);
        this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Schedule frame size reduction after 1 second (you can adjust this)
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                shrinkFrame();
            }
        }, 5000);
    }

    private void shrinkFrame() {
        isShrinking = true;
        int animationDurationMs = 4000; // 4 seconds in milliseconds
        long startTime = System.currentTimeMillis();

        while (true) {
            long currentTime = System.currentTimeMillis();
            double progress = (double) (currentTime - startTime) / animationDurationMs;

            // Calculate new width and height based on progress and target size
            int newWidth = (int) (width + (targetWidth - width) * progress);
            int newHeight = (int) (height + (targetHeight - height) * progress);

            // Set the new frame size
            this.setSize(newWidth, newHeight);

            // Check if target size is reached, break if so
            if (newWidth <= 200 && newHeight <= 200) {
                isShrinking = false;
                break;

            }

            try {
                // Sleep for a short duration to avoid rapid resizing
                Thread.sleep(16); // Adjust sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }







    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double targetFrameTime = 1000000000.0 / fps;
        double delta = 0;
        long timer = System.currentTimeMillis();

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / targetFrameTime;
            lastTime = now;

            while (delta >= 1) {
                gamePanel.checkCollisions();
                gamePanel.move();
                gamePanel.repaint();

                delta--;
            }



            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
            }
        }
    }

   public static void main(String[] args) {
       new GameFrame();

   }
}





