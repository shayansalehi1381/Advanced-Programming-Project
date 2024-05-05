package org.example;

import java.awt.*;
import java.util.Iterator;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class Collectable extends Rectangle {

    int xPos;
    int yPos;
    int width = 8;
    int height = 8;
    int xSpeed;
    int ySpeed;
    boolean catchedByEpsilon = false;
    private static final long REMOVAL_DELAY = 10000;


    public Collectable(TrigorathEnemy trigorathEnemy){
        xPos = trigorathEnemy.xDeath;
        yPos = trigorathEnemy.yDeath;
        GamePanel.collectables.add(this);
        scheduleRemovalIfNotCollected();
    }

    public Collectable(SquareEnemy squareEnemy){
        xPos = squareEnemy.xDeath;
        yPos = squareEnemy.yDeath;
        GamePanel.collectables.add(this);
        scheduleRemovalIfNotCollected();
    }


    private void scheduleRemovalIfNotCollected() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!catchedByEpsilon) {
                    GamePanel.collectables.remove(Collectable.this);
                    timer.cancel(); // Cancel the timer after removal
                }
            }
        }, REMOVAL_DELAY);
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
