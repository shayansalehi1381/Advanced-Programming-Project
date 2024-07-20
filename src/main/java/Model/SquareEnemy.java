package Model;

import View.GameFrame;
import View.GamePanel;

import java.awt.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class SquareEnemy  {
    public GameFrame gameFrame;
    public int HP = 10;
    public int speed = 2;
    public int xPos;
    public int yPos;
   public int width = 15;
   public int height = 15;

    int ID;
    static int nextID = 1;
    public boolean impactedWithEpsilon = false;
    int xDeath;
    int yDeath;
    public int inside = 0;
    public int lastSpeed;

    public SquareEnemy(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        GamePanel.squares.add(this);
        GamePanel.allEnemies.add(this);
        generateRandomPointOutsideFrame(this.gameFrame);
        GamePanel.creation++;
        ID = nextID++;
    }

    public void generateRandomPointOutsideFrame(GameFrame frame) {

        Random random = new Random();
        int x, y;

        do {
            x = random.nextInt(2000 - 0) + 0; // Generate a random x coordinate within the range
            y = random.nextInt(1000 - 0) + 0;
        } while (x >= frame.getX() && x <= frame.getX() + frame.getWidth() &&
                y >= frame.getY() && y <= frame.getY() + frame.getHeight()); // Repeat until the point is outside the frame
        xPos = x;
        yPos = y;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setxDeath(int xDeath) {
        this.xDeath = xDeath;
    }

    public void setyDeath(int yDeath) {
        this.yDeath = yDeath;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
