package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Epsilon extends Rectangle implements KeyListener , MouseListener {
    GameFrame gameFrame;
    public ArrayList<Shot> shots = new ArrayList<>();
    int xPos = 300;
    int yPos = 300;
    int width = 30;
    int height = 30;
    int xSpeed ;
    int ySpeed ;

    boolean S_Key = false;
    boolean W_Key = false;
    boolean D_Key = false;
    boolean A_Key = false;
    boolean collidWithLeft = false;
    boolean collidWithRight = false;
    boolean collidWithUp = false;
    boolean collidWithDown = false;

    public Epsilon(GameFrame gameFrame){

        super();
        this.gameFrame = gameFrame;
    }


    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(xPos,yPos,width,height);

        for (int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            shot.paint(g);
        }
    }

    public void move(){
                if (collidWithRight == true || collidWithLeft == true){
                    xSpeed = 0;
                }
                if (collidWithUp == true || collidWithDown == true){
                    ySpeed = 0;
                }
        xPos += xSpeed;
        yPos += ySpeed;

        for (int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            shot.move();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            W_Key = true;
            if (collidWithDown == true){
                collidWithDown = false;
            }
                if (S_Key == true){
                    setDirectionY(0);
                    move();
                }
                else
                    setDirectionY(-3);
                move();
        }

        if (e.getKeyCode() == KeyEvent.VK_S){
            S_Key = true;
            if (collidWithUp == true){
                collidWithUp = false;
            }

                if (W_Key == true){
                    setDirectionY(0);
                    move();
                }
                else
                    setDirectionY(3);
                move();
        }
        //*****************************************************************
        if (e.getKeyCode() == KeyEvent.VK_D){
            D_Key = true;
            if (collidWithLeft == true){
                collidWithLeft = false;
            }
                if (A_Key == true){
                    setDirectionX(0);
                    move();
                }
                else
                    setDirectionX(3);
                move();
        }

        if (e.getKeyCode() == KeyEvent.VK_A){
            A_Key = true;
            if (collidWithRight == true){
                collidWithRight = false;
            }
                if (D_Key == true){
                    setDirectionX(0);
                    move();
                }
                else
                    setDirectionX(-3);
                move();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W){
            W_Key = false;
           setDirectionY(0);
            move();
        }

        else if (e.getKeyCode() == KeyEvent.VK_S){
            S_Key = false;
            setDirectionY(0);
            move();
        }

        if (e.getKeyCode() == KeyEvent.VK_D){
            D_Key = false;
            setDirectionX(0);
            move();
        }
        if (e.getKeyCode() == KeyEvent.VK_A){
            A_Key = false;
            setDirectionX(0);
            move();
        }
    }

    public void setDirectionX(int number){
        xSpeed = number;
    }
    public void setDirectionY(int number){
        ySpeed = number;
    }


    public void collisionToFrame() {
        {
            if (xPos <= gameFrame.gamePanel.getX()) {
                collidWithLeft = true;
                collidWithRight = false;
            } else if (xPos + width >= gameFrame.gamePanel.getX() + gameFrame.gamePanel.getWidth()) {
                collidWithRight = true;
                collidWithLeft = false;
            }
            else {
                collidWithRight = false;
                collidWithLeft = false;
            }
        }

        {
            if (yPos <= gameFrame.gamePanel.getY()) {
                collidWithUp = true;
                collidWithDown = false;
            } else if (yPos + height >= gameFrame.gamePanel.getY() + gameFrame.gamePanel.getHeight()){
                collidWithDown = true;
                collidWithUp = false;
            }
            else {
                collidWithDown = false;
                collidWithUp = false;
            }
        }
        if (gameFrame.isShrinking == true){
            if (collidWithDown == true){

               yPos = gameFrame.gamePanel.getHeight() - height;
            }

            if (collidWithRight == true){
                xPos = gameFrame.gamePanel.getWidth() - width;
            }


        }


        //******************************************************************
        //collision of Shots to the frame:
        for (Shot shot:shots){
            shot.checkCollisionWithFrame();
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
