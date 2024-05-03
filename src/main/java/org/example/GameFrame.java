package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame implements Runnable {
    GamePanel gamePanel;
    int xFrame = 400;
    int yFrame = 50;
    int width = 700;
    int height = 700;
    int targetWidth = 200;
    int targetHeight = 200;
    int fps = 60;

    Thread gameThread;
    boolean isShrinking = false;
    int newWidth;
    int newHeight;
    int timeDelay = 5000;
    int ShrinkageTime = 0;
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
        this.setBounds(xFrame, yFrame, width, height);
        this.setUndecorated(true);
        //  this.setLocationByPlatform(true);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                while (true){
                    shrinkFrame();

                }

            }
        }, timeDelay);
    }

    private void shrinkFrame() {
        isShrinking = true;
        int animationDurationMs = 10000;
        long startTime = System.currentTimeMillis();

        while (true) {
            long currentTime = System.currentTimeMillis();

            double progress = (double) (currentTime - startTime) / animationDurationMs;

            // Calculate new width and height based on progress and target size
                if (ShrinkageTime >= 1 && width <= 200 && height <= 200) {
                    width = 205;
                    height = 205;

                }
            newWidth = (int) (width + (targetWidth - width) * progress);
            newHeight = (int) (height + (targetHeight - height) * progress);

            this.setSize(newWidth, newHeight);


            // Check if target size is reached, break if so
            if (newWidth <= 200 && newHeight <= 200) {
                width = newWidth;
                height = newHeight;
                isShrinking = false;
                ShrinkageTime++;
                break;

            }


        }
    }

    public void increasePanelHeight() {
        if (isShrinking == true){
            height += 50;
        }
        else {
            height += 5;
        }

            gamePanel.setPreferredSize(new Dimension(width, height)); // Update panel size
            if (collidUpWithShot){

                this.setLocation(getX(),getY() -10);
            }
            else if (collidDownWithShot){

                this.setLocation(getX(),getY() + 10);
            }
            gamePanel.revalidate(); // Revalidate the panel to ensure layout recalculation
            gamePanel.repaint(); // Repaint the panel
            System.out.println(width);
            pack(); // Resize the frame to fit the new panel sizeaw

    }

    public void increasePanelWidth() {
            if (isShrinking == true){
                width += 50;

                if (collidRightWithShot){
                    this.setLocation(getX() + 10,getY());
                }
                else if (collidLeftWithShot){
                    this.setLocation(getX() - 40,getY());
                }
            }
            else {
                width += 5;

                if (collidRightWithShot){
                    this.setLocation(getX() + 5,getY());
                }
                else if (collidLeftWithShot){
                    this.setLocation(getX() - 10,getY());
                }
            }

            gamePanel.setPreferredSize(new Dimension(width, height)); // Update panel size

            gamePanel.revalidate(); // Revalidate the panel to ensure layout recalculation
            gamePanel.repaint(); // Repaint the panel
            System.out.println(width);
            pack(); // Resize the frame to fit the new panel size

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





