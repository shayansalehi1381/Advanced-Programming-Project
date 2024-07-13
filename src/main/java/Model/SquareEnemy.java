package Model;

import View.GameFrame;
import View.GamePanel;

import java.awt.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;

public class SquareEnemy extends Rectangle {
    GameFrame gameFrame;
    public int HP = 10;
    int speed = 2;
    int xPos;
    int yPos;
    int width = 15;
    int height = 15;

    int ID;
    static int nextID = 1;
    boolean impactedWithEpsilon = false;
    int xDeath;
    int yDeath;
    int inside = 0;
    int lastSpeed;

    public SquareEnemy(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        GamePanel.squares.add(this);
        GamePanel.allEnemies.add(this);
        generateRandomPointOutsideFrame(this.gameFrame);
        GamePanel.creation++;
        ID = nextID++;
    }

    public void generateRandomPointOutsideFrame(GameFrame frame) {

        Random random = new Random();
        int x, y;

        do {
            x = random.nextInt(2000 - 0) + 0; // Generate a random x coordinate within the range
            y = random.nextInt(1000 - 0) + 0;
        } while (x >= frame.getX() && x <= frame.getX() + frame.getWidth() &&
                y >= frame.getY() && y <= frame.getY() + frame.getHeight()); // Repeat until the point is outside the frame
        xPos = x;
        yPos = y;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(xPos,yPos,width,height);
    }


    public void moveTowardsEpsilon(Epsilon epsilon) {
        if (gameFrame.contains(this.xPos,this.yPos)){
            inside++;
            if (inside == 1){
                gameFrame.gamePanel.playSE(gameFrame.gamePanel.getSound().enemyInsideSE);
            }
        }


        avoidCollisionWithOtherSquares();
        avoidCollisionWithTriangles();

        int epsilonX = epsilon.xPos;
        int epsilonY = epsilon.yPos;

        // Calculate direction towards epsilon
        int dx = epsilonX - xPos;
        int dy = epsilonY - yPos;

        // Normalize the direction vector
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        if (magnitude < 2) { // Adjust the threshold as needed

            return;
        }
        double normalizedDX = dx / magnitude;
        double normalizedDY = dy / magnitude;

        if (!impactedWithEpsilon) {
            speed = 2;
        }
        xPos += normalizedDX * speed;
        yPos += normalizedDY * speed;
    }


    public boolean checkVerticesHitEpsilon(Epsilon epsilon){
            Rectangle Square = new Rectangle(xPos,yPos,width,height);
            Rectangle epsilonRect = new Rectangle(epsilon.xPos,epsilon.yPos,epsilon.width,epsilon.height);
            if (Square.intersects(epsilonRect)){

                epsilon.handleImpact(this);
                epsilon.HP -=6;
                if (!epsilon.dead){
                    gameFrame.gamePanel.playSE(gameFrame.gamePanel.getSound().damageSE);
                }

                if (epsilon.HP <= 0){
                    epsilon.dead = true;
                }
                return true;
            }
        return false;
    }




        //impact:Shot _ Square
    public void moveBack(Epsilon epsilon) {
        int moveDistance = 10; // Adjust this value as needed

        // Calculate the direction opposite to the epsilon's direction
        int oppositeX = xPos + (xPos - epsilon.xPos);
        int oppositeY = yPos + (yPos - epsilon.yPos);

        // Move the square enemy back for a brief period
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count <= 1) {
                    xPos += ((oppositeX - xPos)) * 2; // Move 1/10th of the distance each iteration
                    yPos += ((oppositeY - yPos)) * 2 ;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 100); // Adjust the delay and period as needed (0 ms delay, 100 ms period)
    }



    //when a shot hits a square the impact affects other squares'movement:
    public void moveOtherSquaresBack(Epsilon epsilon) {


        // Calculate the direction opposite to the epsilon's direction
        int oppositeX = xPos + (xPos - epsilon.xPos);
        int oppositeY = yPos + (yPos - epsilon.yPos);

        // Move the square enemy back for a brief period
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count <= 1) {
                    xPos += (oppositeX - xPos) / 12; // Move 1/10th of the distance each iteration
                    yPos += (oppositeY - yPos) / 12;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 100); // Adjust the delay and period as needed (0 ms delay, 100 ms period)
    }















    // SquareEnemy.java
    public void avoidCollisionWithOtherSquares() {
        for (SquareEnemy otherSquare : GamePanel.squares) {
            if (otherSquare != this ) {
                Rectangle squareRec = new Rectangle(this.xPos,this.yPos,this.width,this.height);
                Rectangle otherRec = new Rectangle(otherSquare.xPos,otherSquare.yPos,otherSquare.width,otherSquare.height);

                if (squareRec.intersects(otherRec)){
                    // Move away from the other square
                    int dx = xPos - otherSquare.xPos;
                    int dy = yPos - otherSquare.yPos;
                    double magnitude = Math.sqrt(dx * dx + dy * dy);
                    double normalizedDX = dx / magnitude;
                    double normalizedDY = dy / magnitude;
                    int avoidanceDistance = 15; // Adjust the avoidance distance as needed
                    xPos += normalizedDX * avoidanceDistance;
                    yPos += normalizedDY * avoidanceDistance;
                    otherSquare.xPos +=  normalizedDX * -avoidanceDistance;
                    otherSquare.yPos += normalizedDX * -avoidanceDistance;
                    moveOtherSquaresBack(gameFrame.gamePanel.epsilon);
                }
            }
        }
    }

    public void avoidCollisionWithTriangles() {
        Rectangle squareRec = new Rectangle(this.xPos,this.yPos,this.width,this.height);
        for (int i = 0; i < GamePanel.triangles.size(); i++) {
            TrigorathEnemy trigorathEnemy = GamePanel.triangles.get(i);
            Polygon triangle = new Polygon(trigorathEnemy.xPoints, trigorathEnemy.yPoints, trigorathEnemy.nPoints);
            if (squareRec.intersects(triangle.getBounds2D())){
                // Move away from the triangle
                int dx = xPos - triangle.xpoints[1];
                int dy = yPos - triangle.ypoints[1];
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double normalizedDX = dx / magnitude;
                double normalizedDY = dy / magnitude;
                int avoidanceDistance = 30; // Adjust the avoidance distance as needed
                xPos += normalizedDX * avoidanceDistance;
                yPos += normalizedDY * avoidanceDistance;


                trigorathEnemy.xP1 += normalizedDX * -avoidanceDistance;
                trigorathEnemy.xP2 += normalizedDX * -avoidanceDistance;
                trigorathEnemy.xP3 += normalizedDX * -avoidanceDistance;
                trigorathEnemy.yP1 += normalizedDX * -avoidanceDistance;
                trigorathEnemy.yP2 += normalizedDX * -avoidanceDistance;
                trigorathEnemy.yP3 += normalizedDX * -avoidanceDistance;
            }
        }
    }



}
