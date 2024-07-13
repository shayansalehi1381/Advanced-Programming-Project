package View;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameFrame extends JFrame implements Runnable {
    public GamePanel gamePanel;
    int xFrame = 400;
    int yFrame = 50;
    int width = 700;
    int height = 700;
    int targetWidth = 200;
    int targetHeight = 200;
    int fps = 60;

    Thread gameThread;
    public boolean isShrinking = false;
    int newWidth;
    int newHeight;
    int timeDelay = 5000;
    int ShrinkageTime = 0;
   public static boolean collidUpWithShot = false;
   public static boolean collidDownWithShot = false;
   public static boolean collidRightWithShot = false;
   public static boolean collidLeftWithShot = false;

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
                    if (width <= 0 && height <= 0){
                        GamePanel.gameOver = true;
                        System.out.println("game finished");
                        break;
                    }
                }

            }
        }, timeDelay);
    }

    private void shrinkFrame() {
        isShrinking = true;
        int animationDurationMs = 20000;
        long startTime = System.currentTimeMillis();

        while (true) {
            long currentTime = System.currentTimeMillis();

            double progress = (double) (currentTime - startTime) / animationDurationMs;
                int target ;
                if (!GamePanel.winTheGame){
                    target = 200;

                }
                else {
                    target = 0;
                }
            // Calculate new width and height based on progress and target size
                if (ShrinkageTime >= 1 && width <= target && height <= target) {
                    if (!GamePanel.winTheGame){
                        width = 220;
                        height = 220;
                    }



                }
            newWidth = (int) (width + (target - width) * progress);
            newHeight = (int) (height + (target - height) * progress);

            this.setSize(newWidth, newHeight);


            // Check if target size is reached, break if so
            if (newWidth <= target && newHeight <= target) {
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
                gamePanel.checkWave();
                gamePanel.checkGameOver();
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





