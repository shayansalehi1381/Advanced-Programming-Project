package org.example;

import java.awt.*;
import java.util.Random;

public class Collectable extends Rectangle {

    int xPos;
    int yPos;
    int width = 8;
    int height = 8;
    int xSpeed;
    int ySpeed;

    public Collectable(TrigorathEnemy trigorathEnemy){
        xPos = trigorathEnemy.xDeath;
        yPos = trigorathEnemy.yDeath;
        GamePanel.collectables.add(this);
    }

    public Collectable(SquareEnemy squareEnemy){
        xPos = squareEnemy.xDeath;
        yPos = squareEnemy.yDeath;
        GamePanel.collectables.add(this);
    }

    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillOval(xPos,yPos,width,height);
    }

    public void move(){
        Random random = new Random();
        xSpeed = random.nextInt(3) - 1;
        ySpeed = random.nextInt(3) - 1;

        xPos += xSpeed;
        yPos += ySpeed;
    }


}
