package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TrigorathEnemy extends Polygon {

    GameFrame gameFrame;

    int HP = 15;

    int xP1 ;
    int xP2 ;
    int xP3 ;
    int yP1 ;
    int yP2 ;
    int yP3 ;
    int[] xPoints = new int[3];
    int[] yPoints = new int[3];
    int nPoints = 3; // Number of vertices
    int speed;

    boolean impactedWithEpsilon = false;

    int lastSpeed;

    int ID;
    static int nextID = 1;

    int xDeath;
    int yDeath;
    int inside = 0;

    static boolean mainImpact;


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


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw the triangle outline
        g2d.setColor(Color.yellow); // Set color of the triangle's outline
        g2d.setStroke(new BasicStroke(3)); // Set the width of the outline
        g2d.drawPolygon(xPoints,yPoints,nPoints);
    }




    public void moveTowardsEpsilon(Epsilon epsilon) {

        if (gameFrame.contains(this.xP2,this.yP2)){
            inside++;
            if (inside == 1){
                gameFrame.gamePanel.playSE(gameFrame.gamePanel.sound.enemyInsideSE);
            }
        }
        int epsilonX = epsilon.xPos;
        int epsilonY = epsilon.yPos;

        // Calculate direction towards epsilon
        int dx = epsilonX - ((xP1+xP3)/2);
        int dy = epsilonY - ((yP2 + yP1)/2);

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


        xP1 += normalizedDX * speed;
        xP2 += normalizedDX * speed;
        xP3 += normalizedDX * speed;
        yP1 += normalizedDY * speed;
        yP2 += normalizedDY * speed;
        yP3 += normalizedDY * speed;

        // Update the xPoints and yPoints arrays
        xPoints[0] = xP1;
        xPoints[1] = xP2;
        xPoints[2] = xP3;
        yPoints[0] = yP1;
        yPoints[1] = yP2;
        yPoints[2] = yP3;


    }


    public void handleImpact(Epsilon epsilon) {
        // Move the triangle back temporarily
        int dx = xP2 - epsilon.xPos;
        int dy = yP2 - epsilon.yPos;
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double normalizedDX = dx / magnitude;
        double normalizedDY = dy / magnitude;
        int impactDistance;
        if (mainImpact){
            impactDistance = 50;
        }
        else impactDistance = 10;
        // Adjust the impact distance as needed

        //********************
        xP1 += normalizedDX * impactDistance;
        xP2 += normalizedDX * impactDistance;
        xP3 += normalizedDX * impactDistance;
        yP1 += normalizedDY * impactDistance;
        yP2 += normalizedDY * impactDistance;
        yP3 += normalizedDY * impactDistance;

        // Update the xPoints and yPoints arrays
        xPoints[0] = xP1;
        xPoints[1] = xP2;
        xPoints[2] = xP3;
       yPoints[0] = yP1;
        yPoints[1] = yP2;
        yPoints[2] = yP3;
    }







    public boolean checkVerticesHitEpsilon(Epsilon epsilon) {
        for (int i = 0; i < nPoints; i++) {
            double distance = Math.sqrt(Math.pow(xPoints[i] - epsilon.xPos, 2) + Math.pow(yPoints[i] - epsilon.yPos, 2));
            // If the distance is less than epsilon, return true
            if (distance < epsilon.width) {
                epsilon.handleImpact(this);
                epsilon.HP -= 10;

                return true;
            }
        }
        return false;
    }






}
