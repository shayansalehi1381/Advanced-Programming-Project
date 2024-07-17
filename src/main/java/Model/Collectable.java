package Model;

import Controller.CollectableController;
import View.GamePanel;

import java.awt.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class Collectable {

    int xPos;
    int yPos;
    public int width = 8;

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int height = 8;
    int xSpeed;
    int ySpeed;
    boolean catchedByEpsilon = false;
    private static final long REMOVAL_DELAY = 10000;
    CollectableController collectableController = new CollectableController();


    public Collectable(TrigorathEnemy trigorathEnemy){
        xPos = trigorathEnemy.xDeath;
        yPos = trigorathEnemy.yDeath;
        collectableController.getCollectables().add(this);
        scheduleRemovalIfNotCollected();
    }

    public Collectable(SquareEnemy squareEnemy){
        xPos = squareEnemy.xDeath;
        yPos = squareEnemy.yDeath;
        collectableController.getCollectables().add(this);
        scheduleRemovalIfNotCollected();
    }


    private void scheduleRemovalIfNotCollected() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!catchedByEpsilon) {
                    CollectableController.collectables.remove(Collectable.this);
                    timer.cancel(); // Cancel the timer after removal
                }
            }
        }, REMOVAL_DELAY);
    }

    public void move(){
        Random random = new Random();
        xSpeed = random.nextInt(3) - 1;
        ySpeed = random.nextInt(3) - 1;

        xPos += xSpeed;
        yPos += ySpeed;
    }


}
