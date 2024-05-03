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
    public static ArrayList<TrigorathEnemy> triangles;


    static int Wave = 1;
    public GamePanel(GameFrame frame){
        super();
        gameFrame = frame;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setBounds(1000,0,getWidth(),getHeight());
        epsilon = new Epsilon(gameFrame);
        triangles = new ArrayList<>();
        setBackground(Color.BLACK);
        setFocusable(true); // Make the panel focusable
      //  requestFocusInWindow(); // Request focus when the frame is initialized
        this.addKeyListener(new AL());
        this.addMouseListener(new ML());
        // Schedule the creation of triangles with a delay
        Timer timer = new Timer(2000, new ActionListener() {
            private int count = 0; // Counter to track the number of triangles created

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Wave == 1 && count < 1) {
                    TrigorathEnemy triangle = new TrigorathEnemy(gameFrame);
                    triangles.add(triangle);
                    count++;
                } else {
                    // Stop the timer after creating the required number of triangles
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.setInitialDelay(2000); // Initial delay before the first triangle creation
        timer.start(); // Start the timer

    }





    public void paint(Graphics g){
        super.paint(g);
        //****************
        epsilon.paint(g);
        for (int i = 0; i < triangles.size(); i++) {
            TrigorathEnemy trigorathEnemy = triangles.get(i);
            trigorathEnemy.paint(g);
        }
    }

    public void move(){
        epsilon.move();
        for (TrigorathEnemy trigorathEnemy:triangles){
            trigorathEnemy.moveTowardsEpsilon(epsilon);

        }
    }


    public void checkCollisions(){
        epsilon.collisionToFrame();
        for (int i = epsilon.shots.size() -1 ; i >= 0 ; i--) {
            Shot shot = epsilon.shots.get(i);
            {
                if (GameFrame.collidDownWithShot == true || GameFrame.collidUpWithShot == true) {

                epsilon.shots.remove(shot);
                    gameFrame.increasePanelHeight();


                } else if (GameFrame.collidLeftWithShot == true || GameFrame.collidRightWithShot == true) {

                    epsilon.shots.remove(shot);
                    gameFrame.increasePanelWidth();

                }

            }
            for (int j = 0; j < triangles.size(); j++) {
                TrigorathEnemy trigorathEnemy = triangles.get(j);
               shot.collidWithTriangle(trigorathEnemy);
            }
            break;
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
