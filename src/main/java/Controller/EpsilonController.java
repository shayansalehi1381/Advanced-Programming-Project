package Controller;

import Model.*;
import View.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class EpsilonController implements KeyListener, MouseListener {
    private Epsilon epsilon;
    private boolean S_Key = false;
    private boolean W_Key = false;
    private boolean D_Key = false;
    private boolean A_Key = false;

    public EpsilonController(Epsilon epsilon) {
        this.epsilon = epsilon;
    }



    public void collisionToFrame() {
        {
            if (epsilon.getxPos() <= epsilon.gameFrame.gamePanel.getX()) {
                epsilon.collidWithLeft = true;
                epsilon.collidWithRight = false;
            } else if (epsilon.getxPos() + epsilon.width >= epsilon.gameFrame.gamePanel.getX() + epsilon.gameFrame.gamePanel.getWidth()) {
                epsilon.collidWithRight = true;
                epsilon.collidWithLeft = false;
            } else {
                epsilon.collidWithRight = false;
                epsilon.collidWithLeft = false;
            }
        }

        {
            if (epsilon.getyPos() <= epsilon.gameFrame.gamePanel.getY()) {
                epsilon.collidWithUp = true;
                epsilon.collidWithDown = false;
            } else if (epsilon.getyPos() + epsilon.height >= epsilon.gameFrame.gamePanel.getY() + epsilon.gameFrame.gamePanel.getHeight()) {
                epsilon.collidWithDown = true;
                epsilon.collidWithUp = false;
            } else {
                epsilon.collidWithDown = false;
                epsilon.collidWithUp = false;
            }
        }
        if (epsilon.gameFrame.isShrinking == true) {
            if (epsilon.collidWithDown == true) {

                epsilon.setyPos(epsilon.gameFrame.gamePanel.getHeight() - epsilon.height);
            }

            if (epsilon.collidWithRight == true) {
                epsilon.setxPos(epsilon.gameFrame.gamePanel.getWidth() - epsilon.width);
            }


        }


        //******************************************************************
        //collision of Shots to the frame:
        for (int i = 0; i < epsilon.shots.size(); i++) {
            Shot shot = epsilon.shots.get(i);
            ShotController shotController = new ShotController(shot,epsilon.gameFrame.gamePanel);
            shotController.checkCollisionWithFrame();
        }
    }

    public void collidWithCollectable(Collectable collectable) {
        Rectangle epsilonRect = new Rectangle(epsilon.getxPos(),epsilon.getyPos(),epsilon.width,epsilon.height);
        Rectangle collectRect = new Rectangle(collectable.getxPos(), collectable.getyPos(), collectable.width, collectable.height);

        if (epsilonRect.intersects(collectRect)) {
            epsilon.XP += 5;
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
    public void handleImpact(TrigorathEnemy triangle) {
        double dx = epsilon.getxPos() - triangle.xP2;
        double dy = epsilon.getyPos() - triangle.yP2;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double normalizedDX = dx / distance;
        double normalizedDY = dy / distance;
        double impactForce = 5;
        ;
        if (distance <= 30) {

            triangle.impactedWithEpsilon = true;
            epsilon.impactTriangle = true;


            if (triangle.impactedWithEpsilon == true) {
                for (int i = 0; i < GamePanel.triangles.size(); i++) {
                    TrigorathEnemy trigorathEnemy = GamePanel.triangles.get(i);
                    if (trigorathEnemy != triangle) {
                        trigorathEnemy.impactedWithEpsilon = true;

                        trigorathEnemy.speed *= -1;

                        trigorathEnemy.lastSpeed = trigorathEnemy.speed;

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                triangle.impactedWithEpsilon = false;
                                epsilon.impactTriangle = false;
                            }
                        }, 100);


                    }
                }
            }


            // Apply a force away from the triangle
            epsilon.xSpeed += normalizedDX * impactForce;
            epsilon.ySpeed += normalizedDY * impactForce;
            triangle.speed *= -1;

            triangle.lastSpeed = triangle.speed;
            // Update position
            epsilon.xPos += epsilon.xSpeed;
            epsilon.yPos += epsilon.ySpeed;

            epsilon.setBounds(epsilon.getxPos(),epsilon.getyPos(),epsilon.width,epsilon.height);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    triangle.impactedWithEpsilon = false;
                    epsilon.impactTriangle = false;

                }
            }, 300);


        }


    }


    public void handleImpact(SquareEnemy squareEnemy) {
        double dx = epsilon.xPos - squareEnemy.getxPos();
        double dy = epsilon.yPos - squareEnemy.getyPos();
        double distance = Math.sqrt(dx * dx + dy * dy);
        double normalizedDX = dx / distance;
        double normalizedDY = dy / distance;
        double impactForce = 5;
        if (distance <= 30) {

            squareEnemy.impactedWithEpsilon = true;
            epsilon.impactSquare = true;


            if (squareEnemy.impactedWithEpsilon == true) {
                for (int i = 0; i < GamePanel.squares.size(); i++) {
                    SquareEnemy squareEnemy1 = GamePanel.squares.get(i);
                    if (squareEnemy1 != squareEnemy) {
                        squareEnemy1.impactedWithEpsilon = true;

                        squareEnemy1.speed *= -1;
                        squareEnemy1.lastSpeed = squareEnemy1.speed;

                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {

                                squareEnemy.impactedWithEpsilon = false;
                                epsilon.impactSquare = false;
                            }
                        }, 100);


                    }
                }
            }


            // Apply a force away from the triangle
            epsilon.xSpeed += normalizedDX * impactForce;
            epsilon.ySpeed += normalizedDY * impactForce;
            squareEnemy.speed *= -1;

            squareEnemy.lastSpeed = squareEnemy.speed;
            // Update position
            epsilon.xPos += epsilon.xSpeed;
            epsilon.yPos += epsilon.ySpeed;

            epsilon.setBounds(epsilon.getxPos(),epsilon.getyPos(),epsilon.width,epsilon.height);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    squareEnemy.impactedWithEpsilon = false;
                    epsilon.impactSquare = false;

                }
            }, 300);


        }

    }





    @Override
    public void keyTyped(KeyEvent e) {}

    public Epsilon getEpsilon() {
        return epsilon;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                W_Key = true;
                handleVerticalMovement(-epsilon.getAcceleration(), S_Key);
                break;
            case KeyEvent.VK_S:
                S_Key = true;
                handleVerticalMovement(3, W_Key);
                break;
            case KeyEvent.VK_D:
                D_Key = true;
                handleHorizontalMovement(3, A_Key);
                break;
            case KeyEvent.VK_A:
                A_Key = true;
                handleHorizontalMovement(-epsilon.getAcceleration(), D_Key);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                W_Key = false;
                epsilon.setDirectionY(0);
                break;
            case KeyEvent.VK_S:
                S_Key = false;
                epsilon.setDirectionY(0);
                break;
            case KeyEvent.VK_D:
                D_Key = false;
                epsilon.setDirectionX(0);
                break;
            case KeyEvent.VK_A:
                A_Key = false;
                epsilon.setDirectionX(0);
                break;
        }
        Check4ButtonsClicked();
        epsilon.move();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Shot shot = new Shot(epsilon);
        epsilon.getShots().add(shot);
        shot.setTarget(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    private void handleVerticalMovement(int direction, boolean oppositeKey) {
        if (GamePanel.winTheGame) {
            resetDirection();
        } else {
            if (oppositeKey) {
                epsilon.setDirectionY(0);
            } else {
                epsilon.setDirectionY(direction);
            }
            epsilon.move();
        }
    }

    private void handleHorizontalMovement(int direction, boolean oppositeKey) {
        if (GamePanel.winTheGame) {
            resetDirection();
        } else {
            if (oppositeKey) {
                epsilon.setDirectionX(0);
            } else {
                epsilon.setDirectionX(direction);
            }
            epsilon.move();
        }
    }

    private void resetDirection() {
        epsilon.setDirectionX(0);
        epsilon.setDirectionY(0);
    }

    private void Check4ButtonsClicked() {
        if (!epsilon.isImpactTriangle() && !W_Key && !S_Key && !D_Key && !A_Key) {
            epsilon.setxSpeed(0);
            epsilon.setySpeed(0);
        }
    }
}
