package org.example;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.*;

public class GamePanel extends JPanel  {
    GameFrame gameFrame;
    Epsilon epsilon;

    public GamePanel(GameFrame frame){
        super();
        gameFrame = frame;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setBounds(1000,0,getWidth(),getHeight());
        epsilon = new Epsilon(gameFrame);
        setBackground(Color.BLACK);
        setFocusable(true); // Make the panel focusable
      //  requestFocusInWindow(); // Request focus when the frame is initialized
        this.addKeyListener(new AL());
        this.addMouseListener(new ML());


    }

    public void paint(Graphics g){
        super.paint(g);
        //****************
        epsilon.paint(g);
    }

    public void move(){
        epsilon.move();
    }


    public void checkCollisions(){
        epsilon.collisionToFrame();
        for (int i = epsilon.shots.size() -1 ; i >= 0 ; i--) {
            Shot shot = epsilon.shots.get(i);
            {
                if (GameFrame.collidDownWithShot == true || GameFrame.collidUpWithShot == true) {
                    gameFrame.increasePanelHeight();
                epsilon.shots.remove(shot);

                } else if (GameFrame.collidLeftWithShot == true || GameFrame.collidRightWithShot == true) {
                    gameFrame.increasePanelWidth();
                    epsilon.shots.remove(shot);
                }

            }
        }
    }


    //action listiner class:
    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            epsilon.keyPressed(e);

        }

        public void keyReleased(KeyEvent e) {
            epsilon.keyReleased(e);

        }
    }

    public class ML extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            epsilon.mouseClicked(e);
        }

    }


}
