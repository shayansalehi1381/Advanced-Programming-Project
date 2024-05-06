package org.example;

import com.sun.jna.platform.mac.SystemB;

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
        g.setColor(Color.MAGENTA);
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



    // Shot.java
    public void collidWithTriangle(TrigorathEnemy triangleEnemy) {
        Rectangle bullet = new Rectangle(xPos, yPos, width, height);
        Polygon triangle = new Polygon(triangleEnemy.xPoints, triangleEnemy.yPoints, triangleEnemy.nPoints);

        if (bullet.intersects(triangle.getBounds2D())) {
            triangleEnemy.HP -= 5;
            epsilon.gameFrame.gamePanel.playSE(epsilon.gameFrame.gamePanel.sound.damageSE);

            if (triangleEnemy.HP <= 0) {
                epsilon.gameFrame.gamePanel.playSE(epsilon.gameFrame.gamePanel.sound.killEnemySE);
                triangleEnemy.xDeath = triangleEnemy.xP2;
                triangleEnemy.yDeath = triangleEnemy.yP2;
                new Collectable(triangleEnemy);
                GamePanel.triangles.remove(triangleEnemy);
            }


            // Apply impact effect to the hit triangle
            TrigorathEnemy.mainImpact = true;
            triangleEnemy.handleImpact(epsilon);
            TrigorathEnemy.mainImpact = false;
            // Apply impact effect to other triangle enemies
            for (TrigorathEnemy otherEnemy : GamePanel.triangles) {
                if (otherEnemy != triangleEnemy) {
                    otherEnemy.handleImpact(epsilon);
                }
            }
            TrigorathEnemy.mainImpact = true;


            for (SquareEnemy otherEnemy : GamePanel.squares) {
                    otherEnemy.moveOtherSquaresBack(epsilon);
            }

            epsilon.shots.remove(this);


        }
    }






    public void collidWithSquare(SquareEnemy squareEnemy){
        Rectangle bullet = new Rectangle(xPos,yPos,width,height);
        Rectangle squareRect = new Rectangle(squareEnemy.xPos,squareEnemy.yPos,squareEnemy.width,squareEnemy.height);
        if (bullet.intersects(squareRect)){
            squareEnemy.HP -= 5;
            epsilon.gameFrame.gamePanel.playSE(epsilon.gameFrame.gamePanel.sound.damageSE);
            if (squareEnemy.HP <= 0){
                epsilon.gameFrame.gamePanel.playSE(epsilon.gameFrame.gamePanel.sound.killEnemySE);
                squareEnemy.xDeath = squareEnemy.xPos;
                squareEnemy.yDeath = squareEnemy.yPos;
                new Collectable(squareEnemy);
                GamePanel.squares.remove(squareEnemy);
            }
            epsilon.shots.remove(this);
            squareEnemy.moveBack(epsilon);


            for (SquareEnemy otherEnemy : GamePanel.squares) {
                if (otherEnemy != squareEnemy) {
                    otherEnemy.moveOtherSquaresBack(epsilon);
                }
            }

                TrigorathEnemy.mainImpact = false;
            for (TrigorathEnemy otherEnemy : GamePanel.triangles) {
                    otherEnemy.handleImpact(epsilon);
            }
            TrigorathEnemy.mainImpact = true;
        }
    }


}
