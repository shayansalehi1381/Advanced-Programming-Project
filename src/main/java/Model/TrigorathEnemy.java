package Model;

import Controller.EpsilonController;
import View.GameFrame;
import View.GamePanel;

import java.awt.*;
import java.util.Random;

public class TrigorathEnemy extends Polygon {

    GameFrame gameFrame;

   public int HP = 15;

   public int xP1 ;
   public int xP2 ;
   public int xP3 ;
   public int yP1 ;
   public int yP2 ;
   public int yP3 ;
   public int[] xPoints = new int[3];
   public int[] yPoints = new int[3];
    public int nPoints = 3; // Number of vertices
    public int speed;

    public boolean impactedWithEpsilon = false;

    public int lastSpeed;

    int ID;
    static int nextID = 1;

   public int xDeath;
   public int yDeath;
    int inside = 0;

   public static boolean mainImpact;


    public TrigorathEnemy(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        generateRandomPointOutsideFrame(this.gameFrame);
        GamePanel.triangles.add(this);
        GamePanel.allEnemies.add(this);
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
        xP1 = x;
        xP2 = x + 10;
        xP3 = x + 20;
        yP1 = y;
        yP2 = y - 20;
        yP3 = y;
        xPoints[0] = xP1;
        xPoints[1] = xP2;
        xPoints[2] = xP3;
        yPoints[0] = yP1;
        yPoints[1] = yP2;
        yPoints[2] = yP3;
    }


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw the triangle outline
        g2d.setColor(Color.yellow); // Set color of the triangle's outline
        g2d.setStroke(new BasicStroke(3)); // Set the width of the outline
        g2d.drawPolygon(xPoints,yPoints,nPoints);
    }




    public void moveTowardsEpsilon(Epsilon epsilon) {

        if (gameFrame.contains(this.xP2,this.yP2)){
            inside++;
            if (inside == 1){
                gameFrame.gamePanel.playSE(gameFrame.gamePanel.getSound().enemyInsideSE);
            }
        }

        avoidCollisionWithOtherTriangles();
        avoidCollisionWithSquares();

        int epsilonX = epsilon.xPos;
        int epsilonY = epsilon.yPos;

        // Calculate direction towards epsilon
        int dx = epsilonX - xP2;
        int dy = epsilonY - yP2;

        // Normalize the direction vector
        double magnitude = Math.sqrt((dx * dx) + (dy * dy));
        if (magnitude < 1) { // Adjust the threshold as needed

            return;
        }
        double normalizedDX = dx / magnitude;
        double normalizedDY = dy / magnitude;

        if (!impactedWithEpsilon) {
            if (magnitude < 60) {
                speed =  1;

            } else {
                speed = 3;
            }
        }




        xP1 += normalizedDX * speed;
        xP2 += normalizedDX * speed;
        xP3 += normalizedDX * speed;
        yP1 += normalizedDY * speed;
        yP2 += normalizedDY * speed;
        yP3 += normalizedDY * speed;

        // Update the xPoints and yPoints arrays
        xPoints[0] = xP1;
        xPoints[1] = xP2;
        xPoints[2] = xP3;
        yPoints[0] = yP1;
        yPoints[1] = yP2;
        yPoints[2] = yP3;


    }


    public void handleImpact(Epsilon epsilon) {
        // Move the triangle back temporarily
        int dx = xP2 - epsilon.xPos;
        int dy = yP2 - epsilon.yPos;
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double normalizedDX = dx / magnitude;
        double normalizedDY = dy / magnitude;
        int impactDistance;
        if (mainImpact){
            impactDistance = 50;
        }
        else impactDistance = 10;
        // Adjust the impact distance as needed

        //********************
        xP1 += normalizedDX * impactDistance;
        xP2 += normalizedDX * impactDistance;
        xP3 += normalizedDX * impactDistance;
        yP1 += normalizedDY * impactDistance;
        yP2 += normalizedDY * impactDistance;
        yP3 += normalizedDY * impactDistance;

        // Update the xPoints and yPoints arrays
        xPoints[0] = xP1;
        xPoints[1] = xP2;
        xPoints[2] = xP3;
       yPoints[0] = yP1;
        yPoints[1] = yP2;
        yPoints[2] = yP3;
    }







    public boolean checkVerticesHitEpsilon(EpsilonController epsilonController) {
        for (int i = 0; i < nPoints; i++) {
            double distance = Math.sqrt(Math.pow(xPoints[i] - epsilonController.getEpsilon().xPos, 2) + Math.pow(yPoints[i] - epsilonController.getEpsilon().yPos, 2));
            // If the distance is less than epsilon, return true
            if (distance < epsilonController.getEpsilon().width) {
                epsilonController.handleImpact(this);
                epsilonController.getEpsilon().HP -= 10;
                if (!epsilonController.getEpsilon().dead){
                    gameFrame.gamePanel.playSE(gameFrame.gamePanel.getSound().damageSE);
                }
                if (epsilonController.getEpsilon().HP <= 0){
                    epsilonController.getEpsilon().dead = true;
                }
                return true;
            }
        }
        return false;
    }








    public void avoidCollisionWithOtherTriangles() {
        Polygon triangle = new Polygon(this.xPoints, this.yPoints, this.nPoints);
        for (TrigorathEnemy otherTriangle : GamePanel.triangles) {
            Polygon otherTrianglew = new Polygon(otherTriangle.xPoints, otherTriangle.yPoints, otherTriangle.nPoints);
            if (otherTriangle != this && triangle.intersects(otherTrianglew.getBounds2D())) {
                // Move away from the other triangle

                int dx = xP2 - otherTriangle.xP2;
                int dy = yP2 - otherTriangle.yP2;
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double normalizedDX = dx / magnitude;
                double normalizedDY = dy / magnitude;
                int avoidanceDistance = 25; // Adjust the avoidance distance as needed
                xP1 += normalizedDX * avoidanceDistance;
                xP2 += normalizedDX * avoidanceDistance;
                xP3 += normalizedDX * avoidanceDistance;
                yP1 += normalizedDY * avoidanceDistance;
                yP2 += normalizedDY * avoidanceDistance;
                yP3 += normalizedDY * avoidanceDistance;



                otherTriangle.xP1 += normalizedDX * -avoidanceDistance;
                otherTriangle.xP2 += normalizedDX * -avoidanceDistance;
                otherTriangle.xP3 += normalizedDX * -avoidanceDistance;
                otherTriangle.yP1 += normalizedDY * -avoidanceDistance;
                otherTriangle.yP2 += normalizedDY * -avoidanceDistance;
                otherTriangle.yP3 += normalizedDY * -avoidanceDistance;
            }
        }
    }



    public void avoidCollisionWithSquares() {
        Polygon triangle = new Polygon(this.xPoints, this.yPoints, this.nPoints);
        for (SquareEnemy square : GamePanel.squares) {
            Rectangle squareRec = new Rectangle(square.xPos,square.yPos,square.width,square.height);
            if (triangle.getBounds2D().intersects(squareRec)) {

                // Move away from the square
                int dx = xP2 - square.xPos;
                int dy = yP2 - square.yPos;
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double normalizedDX = dx / magnitude;
                double normalizedDY = dy / magnitude;
                int avoidanceDistance = 10; // Adjust the avoidance distance as needed
                xP1 += normalizedDX * avoidanceDistance;
                xP2 += normalizedDX * avoidanceDistance;
                xP3 += normalizedDX * avoidanceDistance;
                yP1 += normalizedDY * avoidanceDistance;
                yP2 += normalizedDY * avoidanceDistance;
                yP3 += normalizedDY * avoidanceDistance;


                square.xPos += normalizedDX * -avoidanceDistance;
                square.yPos += normalizedDX * -avoidanceDistance;

            }
        }
    }






}
