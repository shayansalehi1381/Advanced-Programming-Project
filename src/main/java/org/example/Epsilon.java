package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.Timer;

public class Epsilon extends Rectangle implements KeyListener, MouseListener {
    GameFrame gameFrame;
    public ArrayList<Shot> shots = new ArrayList<>();
    int xPos = 300;
    int yPos = 300;
    int width = 30;
    int height = 30;
    int xSpeed;
    int ySpeed;

    boolean S_Key = false;
    boolean W_Key = false;
    boolean D_Key = false;
    boolean A_Key = false;
    boolean collidWithLeft = false;
    boolean collidWithRight = false;
    boolean collidWithUp = false;
    boolean collidWithDown = false;
    int HP = 100;
    int XP = 0;

    boolean impactTriangle = false;

    private final int acceleration = 1;
    private final int deceleration = 1;

    public Epsilon(GameFrame gameFrame) {

        super();
        this.gameFrame = gameFrame;
    }


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.RED); // Set color of the triangle's outline
        g2d.setStroke(new BasicStroke(8));
            g.drawOval(xPos, yPos, width, height);
            for (int i = 0; i < shots.size(); i++) {
                Shot shot = shots.get(i);
                shot.paint(g);
        }
    }

    public void move() {
        if (collidWithRight == true || collidWithLeft == true) {
            xSpeed = 0;
        }
        if (collidWithUp == true || collidWithDown == true) {
            ySpeed = 0;
        }

        if (impactTriangle == false){
            if (W_Key == false && S_Key == false && D_Key == false && A_Key == false){
                xSpeed = 0;
                ySpeed = 0;
            }

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

                        //  System.out.println("hi");
                        trigorathEnemy.impactedWithEpsilon = true;

                        trigorathEnemy.speed *= -1;
                        System.out.println("ID:"+trigorathEnemy.ID + trigorathEnemy.speed);
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
            System.out.println(triangle.speed);
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



    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            W_Key = true;
            if (collidWithDown == true) {
                collidWithDown = false;
            }
            if (S_Key == true) {
                setDirectionY(0);
                move();
            } else
                setDirectionY(-acceleration);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_S) {
            S_Key = true;
            if (collidWithUp == true) {
                collidWithUp = false;
            }

            if (W_Key == true) {
                setDirectionY(0);
                move();
            } else
                setDirectionY(3);
            move();
        }
        //*****************************************************************
        if (e.getKeyCode() == KeyEvent.VK_D) {
            D_Key = true;
            if (collidWithLeft == true) {
                collidWithLeft = false;
            }
            if (A_Key == true) {
                setDirectionX(0);
                move();
            } else
                setDirectionX(3);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_A) {
            A_Key = true;
            if (collidWithRight == true) {
                collidWithRight = false;
            }
            if (D_Key == true) {
                setDirectionX(0);
                move();
            } else
                setDirectionX(-acceleration);
            move();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            W_Key = false;
            setDirectionY(0);
            move();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            S_Key = false;
            setDirectionY(0);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_D) {
            D_Key = false;
            setDirectionX(0);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            A_Key = false;
            setDirectionX(0);
            move();
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
            Iterator<Collectable> iterator = GamePanel.collectables.iterator();
            while (iterator.hasNext()) {
                Collectable nextCollectable = iterator.next();
                if (nextCollectable == collectable) {
                    iterator.remove();
                    break; // Stop the loop once the collectable is removed
                }
            }
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        Shot shot = new Shot(this);
        shots.add(shot);
        shot.targetX = e.getX();
        shot.targetY = e.getY();
        shot.setTarget(shot.targetX, shot.targetY);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
