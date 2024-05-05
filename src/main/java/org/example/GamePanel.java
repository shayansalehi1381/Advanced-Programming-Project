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
    public static ArrayList<SquareEnemy> squares;
    public static ArrayList<Object> allEnemies;
    public static ArrayList<Collectable> collectables;
    public static int creation = 0;

    public static boolean winTheGame= false;


    static int Wave = 1;
    public GamePanel(GameFrame frame){
        super();
        gameFrame = frame;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        setBounds(1000,0,getWidth(),getHeight());
        epsilon = new Epsilon(gameFrame);
        triangles = new ArrayList<>();
        squares = new ArrayList<>();
        allEnemies = new ArrayList<>();
        collectables = new ArrayList<>();
        setBackground(Color.BLACK);
        setFocusable(true); // Make the panel focusable
      //  requestFocusInWindow(); // Request focus when the frame is initialized
        this.addKeyListener(new AL());
        this.addMouseListener(new ML());
        new Wave(gameFrame);
    }


    public void checkWave() {
        if (creation >= 1){
            if (Wave == 1) {
                if (triangles.isEmpty()) {
                    if (squares.isEmpty()) {
                        System.out.println("Wave 1 passed!");
                        Wave = 2;
                        creation++;
                        new Wave(gameFrame);
                    }
                }
            }
    }
         if (Wave == 2){
             if (triangles.isEmpty()){
                 if (squares.isEmpty()){
                     System.out.println("Wave 2 passed!");
                     Wave = 3;
                     new Wave(gameFrame);
                 }
             }
         }


            if (Wave == 3){
                if (triangles.isEmpty()){
                    if (squares.isEmpty()){
                       winTheGame = true;
                    }
                }
            }
        }






    public void paint(Graphics g){
        super.paint(g);
        //****************
        epsilon.paint(g);
        for (int i = 0; i < triangles.size(); i++) {
            TrigorathEnemy trigorathEnemy = triangles.get(i);
            trigorathEnemy.paint(g);
        }
        for (int i = 0; i < squares.size(); i++) {
            SquareEnemy squareEnemy = squares.get(i);
            squareEnemy.paint(g);
        }//*********************************************

        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            collectable.paint(g);
        }


    }

    public void move(){
        epsilon.move();
        for (TrigorathEnemy trigorathEnemy:triangles){
            trigorathEnemy.moveTowardsEpsilon(epsilon);

        }


        for (SquareEnemy squareEnemy:squares){
            squareEnemy.moveTowardsEpsilon(epsilon);

        }

        //***************************
        //coolectable moving

        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            collectable.move();
        }
    }


    public void checkCollisions(){
        epsilon.collisionToFrame();


        //shots hit the frame
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


            // shots hit the triangles
            for (int j = 0; j < triangles.size(); j++) {
                TrigorathEnemy trigorathEnemy = triangles.get(j);
               shot.collidWithTriangle(trigorathEnemy);
            }



            // shots hit the squares
            for (int j = 0; j < squares.size(); j++) {
                SquareEnemy squareEnemy = squares.get(j);
                shot.collidWithSquare(squareEnemy);
            }

            break;
        }


        //triangles hit the epsilon
        for (int i = 0; i < triangles.size(); i++) {
            TrigorathEnemy trigorathEnemy = triangles.get(i);
            trigorathEnemy.checkVerticesHitEpsilon(epsilon);
        }


        //squares hit epsilon
        for (int i = 0; i < squares.size(); i++) {
            SquareEnemy squareEnemy = squares.get(i);
            squareEnemy.checkVerticesHitEpsilon(epsilon);
        }


        //epsilon hits the Collectables
        for (int i = 0; i < collectables.size(); i++) {
            Collectable collectable = collectables.get(i);
            epsilon.collidWithCollectable(collectable);
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
