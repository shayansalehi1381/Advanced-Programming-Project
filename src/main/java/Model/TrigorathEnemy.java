package Model;

import Controller.EpsilonController;
import View.GameFrame;
import View.GamePanel;

import java.awt.*;
import java.util.Random;

public class TrigorathEnemy  {

   public GameFrame gameFrame;

   public int HP = 15;

   public int xP1 ;
   public int xP2 ;
   public int xP3 ;
   public int yP1 ;
   public int yP2 ;
   public int yP3 ;
   public int[] xPoints = new int[3];
   public int[] yPoints = new int[3];
    public int nPoints = 3; // Number of vertices
    public int speed;

    public boolean impactedWithEpsilon = false;

    public int lastSpeed;

    int ID;
    static int nextID = 1;

   public int xDeath;
   public int yDeath;
   public int inside = 0;

   public static boolean mainImpact;


    public TrigorathEnemy(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        generateRandomPointOutsideFrame(this.gameFrame);
        GamePanel.triangles.add(this);
        GamePanel.allEnemies.add(this);
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
        xP1 = x;
        xP2 = x + 10;
        xP3 = x + 20;
        yP1 = y;
        yP2 = y - 20;
        yP3 = y;
        xPoints[0] = xP1;
        xPoints[1] = xP2;
        xPoints[2] = xP3;
        yPoints[0] = yP1;
        yPoints[1] = yP2;
        yPoints[2] = yP3;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }

    public int[] getxPoints() {
        return xPoints;
    }

    public int[] getyPoints() {
        return yPoints;
    }

    public int getnPoints() {
        return nPoints;
    }
}
