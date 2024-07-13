package View;

import Controller.Sound;
import Model.Collectable;
import Model.Epsilon;
import Model.Shot;
import Model.SquareEnemy;
import Model.TrigorathEnemy;
import Controller.Wave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;
import java.util.Timer;

public class GamePanel extends JPanel implements KeyListener{
    GameFrame gameFrame;
    public Epsilon epsilon;

    public static ArrayList<TrigorathEnemy> triangles;
    public static ArrayList<SquareEnemy> squares;
    public static ArrayList<Object> allEnemies;
    public static ArrayList<Collectable> collectables;
    public static int creation = 0;

    public static boolean winTheGame= false;

    Sound sound = new Sound();

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }

   public static int Wave = 1;
    public static boolean gameOver = false;
    public static long numberOfGameOver;
    boolean showInfo = false;
    long startTime;
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
        this.setLayout(new BorderLayout());
        this.addKeyListener(new AL());
        this.addKeyListener(this);
        this.addMouseListener(new ML());
        new Wave(gameFrame);
        playMusic(sound.themeSong);
        startTime = System.currentTimeMillis();

    }


    public void checkWave() {
        if (creation >= 1){
            if (Wave == 1) {
                if (triangles.isEmpty()) {
                    if (squares.isEmpty()) {
                        System.out.println("Wave 1 passed!");
                        playSE(sound.waveEndSE);
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
                     playSE(sound.waveEndSE);
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

        public void checkGameOver(){
        if (gameOver){

            if (numberOfGameOver == 1){
                playSE(sound.gameOverSE);
                System.out.println("game over");
            }
        }
        }

    public void winTheGame() {
        if (winTheGame) {
            int epsilonFinalWidth = 10000; // Final width and height for Epsilon

            Timer timer = new Timer();

                int currentWidth = epsilon.width; // Initial width of Epsilon
                int step = 2; // Step size for each iteration


                    if (currentWidth < epsilonFinalWidth ) {
                        // Increase the size of Epsilon
                        currentWidth += step;
                        epsilon.width = currentWidth;
                        epsilon.height = currentWidth;
                        epsilon.xPos += step;
                        epsilon.yPos += step;
                        repaint();
                    } else {
                        // Stop the timer when Epsilon reaches the final size
                        timer.cancel();
                    }

             // Run the timer task every 50 milliseconds
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


        //draw information in the corners:
      paintInformation(g);
    }

    public void paintInformation(Graphics g){
        if (showInfo){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.drawString("Wave: "+Wave,gameFrame.gamePanel.getX()+10 ,gameFrame.gamePanel.getY()+20);
            g.drawString("XP: "+epsilon.XP,gameFrame.gamePanel.getX()+gameFrame.gamePanel.getWidth()-70 ,gameFrame.gamePanel.getY()+20);

            g.drawString("HP: "+epsilon.HP,gameFrame.gamePanel.getX()+10 ,gameFrame.gamePanel.getY()+gameFrame.gamePanel.getHeight()-20);

            g.setFont(new Font("Arial",Font.PLAIN,10));
            g.drawString("Time Eplased: "+getElapsedTime(),gameFrame.gamePanel.getX()+gameFrame.gamePanel.getWidth()-100 ,gameFrame.gamePanel.getY()+gameFrame.gamePanel.getHeight()-20);

        }
    }

    public long getElapsedTime() {
        return (System.currentTimeMillis() - startTime)/1000; // Calculate the elapsed time since the game started
    }


    public void playMusic(File file){
        sound.setFile(file);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }


    public void playSE(File file){
        sound.setFile(file);
        sound.play();
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
        winTheGame();
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

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'i' || e.getKeyChar() == 'I') { // Check if 'I' key is pressed
            showInfo = !showInfo; // Toggle the value of showInfo
            repaint(); // Repaint the panel to reflect the change

            if (showInfo) { // If showInfo is true
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        showInfo = false; // Set showInfo back to false after 3 seconds
                        repaint(); // Repaint the panel to reflect the change
                    }
                }, 3000); // Schedule the task to run after 3000 milliseconds (3 seconds)
            }
        }
    }



    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

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
