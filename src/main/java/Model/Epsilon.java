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
    GameFrame gameFrame;
    public ArrayList<Shot> shots = new ArrayList<>();
    public int xPos = 300;
    public int yPos = 300;
    public int width = 30;
    public int height = 30;
    public int xSpeed;
    public int ySpeed;
    boolean collidWithLeft = false;
    boolean collidWithRight = false;
    boolean collidWithUp = false;
    boolean collidWithDown = false;
    public int HP = 100;
    public int XP = 0;

    public static int Movement = 1;


    boolean impactTriangle = false;
    boolean impactSquare = false;


    private  int acceleration = 1;
    private  int deceleration = 1;
    public boolean dead = false;

    public Epsilon(GameFrame gameFrame) {

        super();
        this.gameFrame = gameFrame;

        if (Movement == 1){
            acceleration = 1;
        }
        else if (Movement == 2){
            acceleration = 2;
        }
        else if (Movement == 3){
            acceleration = 3;
        }
    }


    public void paint(Graphics g) {
        if (!dead){
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED); // Set color of the triangle's outline
            g2d.setStroke(new BasicStroke(8));
            g.fillOval(xPos, yPos, width, height);
            for (int i = 0; i < shots.size(); i++) {
                Shot shot = shots.get(i);
                shot.paint(g);
            }

        }else {
            GamePanel.gameOver = true;
            GamePanel.numberOfGameOver++;
        }


    }

    public void move() {
        if (collidWithRight == true || collidWithLeft == true) {
            xSpeed = 0;
        }
        if (collidWithUp == true || collidWithDown == true) {
            ySpeed = 0;
        }


        xPos += xSpeed;
        yPos += ySpeed;

        for (int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            shot.move();
        }
    }


    public void handleImpact(TrigorathEnemy triangle) {
        double dx = xPos - triangle.xP2;
        double dy = yPos - triangle.yP2;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double normalizedDX = dx / distance;
        double normalizedDY = dy / distance;
        double impactForce = 5;;
        if (distance <= 30) {

            triangle.impactedWithEpsilon = true;
            this.impactTriangle = true;


            if (triangle.impactedWithEpsilon == true){
                for (int i = 0; i < GamePanel.triangles.size(); i++) {
                    TrigorathEnemy trigorathEnemy = GamePanel.triangles.get(i);
                    if (trigorathEnemy != triangle){
                        trigorathEnemy.impactedWithEpsilon = true;

                        trigorathEnemy.speed *= -1;

                        trigorathEnemy.lastSpeed = trigorathEnemy.speed;

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                triangle.impactedWithEpsilon = false;
                                impactTriangle = false;
                            }
                        }, 100);



                    }
                }
            }



            // Apply a force away from the triangle
            xSpeed += normalizedDX * impactForce;
            ySpeed += normalizedDY * impactForce;
            triangle.speed *= -1;

            triangle.lastSpeed = triangle.speed;
            // Update position
            xPos += xSpeed;
            yPos += ySpeed;

            this.setBounds(xPos, yPos, width, height);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    triangle.impactedWithEpsilon = false;
                    impactTriangle = false;

                }
            }, 300);


        }


    }






    public void handleImpact(SquareEnemy squareEnemy){
        double dx = xPos - squareEnemy.xPos;
        double dy = yPos - squareEnemy.yPos;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double normalizedDX = dx / distance;
        double normalizedDY = dy / distance;
        double impactForce = 5;
        if (distance <= 30) {

            squareEnemy.impactedWithEpsilon = true;
            this.impactSquare = true;


            if (squareEnemy.impactedWithEpsilon == true){
                for (int i = 0; i < GamePanel.squares.size(); i++) {
                    SquareEnemy squareEnemy1 = GamePanel.squares.get(i);
                    if (squareEnemy1 != squareEnemy){
                        squareEnemy1.impactedWithEpsilon = true;

                        squareEnemy1.speed *= -1;
                        squareEnemy1.lastSpeed = squareEnemy1.speed;

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                squareEnemy.impactedWithEpsilon = false;
                                impactSquare = false;
                            }
                        }, 100);



                    }
                }
            }



            // Apply a force away from the triangle
            xSpeed += normalizedDX * impactForce;
            ySpeed += normalizedDY * impactForce;
            squareEnemy.speed *= -1;

            squareEnemy.lastSpeed = squareEnemy.speed;
            // Update position
            xPos += xSpeed;
            yPos += ySpeed;

            this.setBounds(xPos, yPos, width, height);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    squareEnemy.impactedWithEpsilon = false;
                    impactSquare = false;

                }
            }, 300);


        }

    }






    public void setDirectionX(int number) {
        if (number == 0) {
            // Decelerate
            while (xSpeed != 0){
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

            while (ySpeed != 0){
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



    public void collisionToFrame() {
        {
            if (xPos <= gameFrame.gamePanel.getX()) {
                collidWithLeft = true;
                collidWithRight = false;
            } else if (xPos + width >= gameFrame.gamePanel.getX() + gameFrame.gamePanel.getWidth()) {
                collidWithRight = true;
                collidWithLeft = false;
            } else {
                collidWithRight = false;
                collidWithLeft = false;
            }
        }

        {
            if (yPos <= gameFrame.gamePanel.getY()) {
                collidWithUp = true;
                collidWithDown = false;
            } else if (yPos + height >= gameFrame.gamePanel.getY() + gameFrame.gamePanel.getHeight()) {
                collidWithDown = true;
                collidWithUp = false;
            } else {
                collidWithDown = false;
                collidWithUp = false;
            }
        }
        if (gameFrame.isShrinking == true) {
            if (collidWithDown == true) {

                yPos = gameFrame.gamePanel.getHeight() - height;
            }

            if (collidWithRight == true) {
                xPos = gameFrame.gamePanel.getWidth() - width;
            }


        }


        //******************************************************************
        //collision of Shots to the frame:
        for (int i = 0; i < this.shots.size(); i++) {
            Shot shot = shots.get(i);
            shot.checkCollisionWithFrame();
        }
    }

    public void collidWithCollectable(Collectable collectable){
        Rectangle epsilonRect = new Rectangle(xPos, yPos, width, height);
        Rectangle collectRect = new Rectangle(collectable.xPos, collectable.yPos, collectable.width, collectable.height);

        if (epsilonRect.intersects(collectRect)) {
            this.XP += 5;
            collectable.catchedByEpsilon = true;

            // Use iterator to remove the collectable
            Iterator<Collectable> iterator = CollectableController.collectables.iterator();
            while (iterator.hasNext()) {
                Collectable nextCollectable = iterator.next();
                if (nextCollectable == collectable) {
                    iterator.remove();
                    break; // Stop the loop once the collectable is removed
                }
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
