package Model;

import Controller.SquareEnemyController;
import Model.Collectable;
import Model.Epsilon;
import Model.SquareEnemy;
import Model.TrigorathEnemy;
import View.GameFrame;
import View.GamePanel;

import java.awt.*;

public class Shot {
    Epsilon epsilon;
    int xPos;
    int yPos;

    public int width = 5;
    public int height = 5;
    int speed = 10;
    boolean moving = false;
    int targetX;
    int targetY;
    int directionX;
    int directionY;
    boolean removedFromList = false;
    int ID;
    static int nextID = 1;

    public Shot(Epsilon epsilon) {
        this.epsilon = epsilon;
        ID = nextID++;
        xPos = epsilon.xPos + 15;
        yPos = epsilon.yPos + 15;
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

    // Shot.java
    public void collidWithTriangle(TrigorathEnemy triangleEnemy) {
        Rectangle bullet = new Rectangle(xPos, yPos, width, height);
        Polygon triangle = new Polygon(triangleEnemy.xPoints, triangleEnemy.yPoints, triangleEnemy.nPoints);

        if (bullet.intersects(triangle.getBounds2D())) {
            triangleEnemy.HP -= 5;
            epsilon.gameFrame.gamePanel.playSE(epsilon.gameFrame.gamePanel.getSound().damageSE);

            if (triangleEnemy.HP <= 0) {
                epsilon.gameFrame.gamePanel.playSE(epsilon.gameFrame.gamePanel.getSound().killEnemySE);
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
                SquareEnemyController otherEnemyController = new SquareEnemyController(otherEnemy);
                otherEnemyController.moveOtherSquaresBack(epsilon);
            }

            epsilon.shots.remove(this);


        }
    }


    public void collidWithSquare(SquareEnemyController squareEnemyController) {
        Rectangle bullet = new Rectangle(xPos, yPos, width, height);
        Rectangle squareRect = new Rectangle(squareEnemyController.squareEnemy.xPos, squareEnemyController.squareEnemy.yPos, squareEnemyController.squareEnemy.width, squareEnemyController.squareEnemy.height);
        if (bullet.intersects(squareRect)) {
            squareEnemyController.squareEnemy.HP -= 5;
            epsilon.gameFrame.gamePanel.playSE(epsilon.gameFrame.gamePanel.getSound().damageSE);
            if (squareEnemyController.squareEnemy.HP <= 0) {
                epsilon.gameFrame.gamePanel.playSE(epsilon.gameFrame.gamePanel.getSound().killEnemySE);
                squareEnemyController.squareEnemy.xDeath = squareEnemyController.squareEnemy.xPos;
                squareEnemyController.squareEnemy.yDeath = squareEnemyController.squareEnemy.yPos;
                new Collectable(squareEnemyController.squareEnemy);
                GamePanel.squares.remove(squareEnemyController.squareEnemy);
            }
            epsilon.shots.remove(this);
            squareEnemyController.moveBack(epsilon);


            for (SquareEnemy otherEnemy : GamePanel.squares) {
                if (otherEnemy != squareEnemyController.squareEnemy) {
                    SquareEnemyController otherEnemyController = new SquareEnemyController(otherEnemy);
                    otherEnemyController.moveOtherSquaresBack(epsilon);
                }
            }

            TrigorathEnemy.mainImpact = false;
            for (TrigorathEnemy otherEnemy : GamePanel.triangles) {
                otherEnemy.handleImpact(epsilon);
            }
            TrigorathEnemy.mainImpact = true;
        }
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }
    public Epsilon getEpsilon() {
        return epsilon;
    }

}
