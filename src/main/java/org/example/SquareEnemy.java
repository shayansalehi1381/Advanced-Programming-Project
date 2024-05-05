package org.example;

import java.awt.*;
import java.util.Random;

public class SquareEnemy extends Rectangle {
    GameFrame gameFrame;
    int HP = 10;
    int speed = 2;
    int xPos;
    int yPos;
    int width = 15;
    int height = 15;

    int ID;
    static int nextID = 1;
    boolean impactedWithEpsilon = false;

    int topLeftX = xPos;
    int topLeftY = yPos;
    int topRightX = xPos + width;
    int topRightY = yPos;
    int bottomLeftX = xPos;
    int bottomLeftY = yPos + height;
    int bottomRightX = xPos + width;
    int bottomRightY = yPos + height;

    public SquareEnemy(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        GamePanel.squares.add(this);
        generateRandomPointOutsideFrame(this.gameFrame);
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

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(xPos,yPos,width,height);
    }


    public void moveTowardsEpsilon(Epsilon epsilon) {
        int epsilonX = epsilon.xPos;
        int epsilonY = epsilon.yPos;

        // Calculate direction towards epsilon
        int dx = epsilonX - xPos;
        int dy = epsilonY - yPos;

        // Normalize the direction vector
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        if (magnitude < 2) { // Adjust the threshold as needed

            return;
        }
        double normalizedDX = dx / magnitude;
        double normalizedDY = dy / magnitude;

        if (!impactedWithEpsilon) {
            int acceleration = 1;
            int deceleration = 1;
            if (magnitude >= 150) {
                // Accelerate to maximum speed of 3
                speed = Math.min(speed + acceleration, 3);
            } else {
                // Decelerate with a minimum speed of 1
                speed = Math.max(speed - deceleration, 1);
            }
        }
        xPos += normalizedDX * speed;
        yPos += normalizedDY * speed;
    }


    public boolean checkVerticesHitEpsilon(Epsilon epsilon){
            Rectangle Square = new Rectangle(xPos,yPos,width,height);
            Rectangle epsilonRect = new Rectangle(epsilon.xPos,epsilon.yPos,epsilon.width,epsilon.height);
            if (Square.intersects(epsilonRect)){

                return true;
            }
        return false;
    }
}
