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
    int newWidth;
    int newHeight;

    static boolean collidUpWithShot = false;
    static boolean collidDownWithShot = false;
    static boolean collidRightWithShot = false;
    static boolean collidLeftWithShot = false;

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

             newWidth = (int) (700 + (targetWidth - 700) * progress);
             newHeight = (int) (700 + (targetHeight - 700) * progress);
             width = newWidth;
             height = newHeight;
            // Set the new frame size
            this.setSize(width, height);

            // Check if target size is reached, break if so
            if (newWidth <= 200 && newHeight <= 200) {
                width = newWidth;
                height = newHeight;
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
    public void increasePanelHeight() {
            if (isShrinking == false){
                height += 10;
                gamePanel.setPreferredSize(new Dimension(width, height)); // Update panel size
                gamePanel.revalidate(); // Revalidate the panel to ensure layout recalculation
                gamePanel.repaint(); // Repaint the panel
                System.out.println(width);
                pack(); // Resize the frame to fit the new panel sizeaw
            }
    }
    public void increasePanelWidth() {
        if (isShrinking == false){
            width += 10;
            gamePanel.setPreferredSize(new Dimension(width, height)); // Update panel size
            gamePanel.revalidate(); // Revalidate the panel to ensure layout recalculation
            gamePanel.repaint(); // Repaint the panel
            System.out.println(width);
            pack(); // Resize the frame to fit the new panel sizeaw
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





