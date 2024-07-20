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
