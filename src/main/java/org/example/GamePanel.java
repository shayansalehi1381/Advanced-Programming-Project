package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel  {
    GameFrame gameFrame;
    Epsilon epsilon;

    public GamePanel(GameFrame frame){
        super();
        gameFrame = frame;
        epsilon = new Epsilon();
        setBackground(Color.BLACK);
        setFocusable(true); // Make the panel focusable
        requestFocusInWindow(); // Request focus when the frame is initialized
        this.addKeyListener(new AL());
        this.setLayout(null);

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


}
