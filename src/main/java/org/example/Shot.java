package org.example;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Shot extends Rectangle {
        Epsilon epsilon;
    int xPos;
    int yPos;
    int width = 5;
    int height = 5;
    int speed = 10;
    boolean moving = false;
    int targetX;
    int targetY;
    int directionX;
    int directionY;
    boolean removedFromList = false;
    int ID;
    static int nextID = 1;

    public Shot(Epsilon epsilon){
        this.epsilon = epsilon;
        ID = nextID++;
        xPos = epsilon.xPos + 15;
        yPos = epsilon.yPos + 15;
    }

    public void paint(Graphics g){
        g.setColor(Color.yellow);
        g.fillOval(xPos,yPos,width,height);
    }
    public void move() {
        if (moving) {
            xPos += directionX;
            yPos += directionY;
        }
    }

    public void setTarget(int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;

        int dx = this.targetX - xPos;
        int dy = this.targetY - yPos;

        double distance = Math.sqrt(dx * dx + dy * dy);
        double normalizedDx = (dx / distance) * speed;
        double normalizedDy = (dy / distance) * speed;

        // Set the direction
        directionX = (int) Math.round(normalizedDx);
        directionY = (int) Math.round(normalizedDy);
        moving = true; // Start moving towards the target
    }

    public void checkCollisionWithFrame(){
        {
            if (xPos + width >= epsilon.gameFrame.gamePanel.getX() + epsilon.gameFrame.gamePanel.getWidth()) {
                GameFrame.collidRightWithShot = true;
                GameFrame.collidLeftWithShot = false;

            } else if (xPos <= epsilon.gameFrame.gamePanel.getX()) {
                GameFrame.collidRightWithShot = false;
                GameFrame.collidLeftWithShot = true;
            } else {
                GameFrame.collidRightWithShot = false;
                GameFrame.collidLeftWithShot = false;
            }
        }

        {
            if (yPos <= epsilon.gameFrame.gamePanel.getY()){
                GameFrame.collidUpWithShot = true;
                GameFrame.collidDownWithShot = false;
            }
            else if (yPos + height >= epsilon.gameFrame.gamePanel.getY() + epsilon.gameFrame.gamePanel.getHeight()){
                GameFrame.collidUpWithShot = false;
                GameFrame.collidDownWithShot = true;
            }
            else {
                GameFrame.collidUpWithShot = false;
                GameFrame.collidDownWithShot = false;
            }
        }

    }

}
