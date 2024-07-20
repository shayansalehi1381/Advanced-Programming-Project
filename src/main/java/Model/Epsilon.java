package Model;

import Controller.CollectableController;
import View.GameFrame;
import View.GamePanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Timer;

public class Epsilon extends Rectangle {
    public GameFrame gameFrame;
    public ArrayList<Shot> shots = new ArrayList<>();
    public int xPos = 300;
    public int yPos = 300;
    public int width = 30;
    public int height = 30;
    public int xSpeed;
    public int ySpeed;
    public boolean collidWithLeft = false;
    public boolean collidWithRight = false;
    public boolean collidWithUp = false;
    public boolean collidWithDown = false;
    public int HP = 100;
    public int XP = 0;

    public static int Movement = 1;


   public boolean impactTriangle = false;
   public boolean impactSquare = false;


    private int acceleration = 1;
    private int deceleration = 1;
    public boolean dead = false;

    public Epsilon(GameFrame gameFrame) {
        super(300, 300, 30, 30);
        this.gameFrame = gameFrame;
        setAcceleration(Movement);
    }


    public void move() {
        if (collidWithRight || collidWithLeft) xSpeed = 0;
        if (collidWithUp || collidWithDown) ySpeed = 0;

        xPos += xSpeed;
        yPos += ySpeed;

        for (int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            shot.move();
        }
    }

    private void setAcceleration(int movement) {
        this.acceleration = Math.min(movement, 3);
    }


    public void setDirectionX(int number) {
        if (number == 0) {
            // Decelerate
            while (xSpeed != 0) {
                if (xSpeed > 0) {
                    xSpeed -= deceleration;
                } else if (xSpeed < 0) {
                    xSpeed += deceleration;
                }

            }

        } else {
            xSpeed += number;
            // Limit maximum speed
            if (xSpeed > 3) {
                xSpeed = 3;
            } else if (xSpeed < -3) {
                xSpeed = -3;
            }
            // Limit minimum speed
            if (Math.abs(xSpeed) < 2) {
                xSpeed = (xSpeed > 0) ? 2 : -2;
            }
        }
    }

    public void setDirectionY(int number) {
        if (number == 0) {
            // Decelerate

            while (ySpeed != 0) {
                if (ySpeed > 0) {
                    ySpeed -= deceleration;
                } else if (ySpeed < 0) {
                    ySpeed += deceleration;
                }
            }

        } else {
            ySpeed += number;
            // Limit maximum speed
            if (ySpeed > 3) {
                ySpeed = 3;
            } else if (ySpeed < -3) {
                ySpeed = -3;
            }
            // Limit minimum speed
            if (Math.abs(ySpeed) < 2) {
                ySpeed = (ySpeed > 0) ? 2 : -2;
            }
        }
    }


    public ArrayList<Shot> getShots() {
        return shots;
    }


    public int getxPos() {
        return xPos;
    }


    public int getyPos() {
        return yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public double getWidth() {
        return width;
    }


    public double getHeight() {
        return height;
    }


    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }


    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }


    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getXP() {
        return XP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }


    public boolean isImpactTriangle() {
        return impactTriangle;
    }


    public int getAcceleration() {
        return acceleration;
    }


    public boolean isDead() {
        return dead;
    }


}
