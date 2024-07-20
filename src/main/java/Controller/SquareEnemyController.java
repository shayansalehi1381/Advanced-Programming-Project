package Controller;

import Model.Epsilon;
import Model.SquareEnemy;
import Model.TrigorathEnemy;
import View.GamePanel;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class SquareEnemyController {

    public SquareEnemy squareEnemy;

    public SquareEnemyController(SquareEnemy squareEnemy){
        this.squareEnemy = squareEnemy;
    }



    public void moveTowardsEpsilon(Epsilon epsilon) {
        if (squareEnemy.getGameFrame().contains(squareEnemy.getxPos(),squareEnemy.getyPos())){
            squareEnemy.inside++;
            if (squareEnemy.inside == 1){
                squareEnemy.getGameFrame().gamePanel.playSE(squareEnemy.getGameFrame().gamePanel.getSound().enemyInsideSE);
            }
        }


        avoidCollisionWithOtherSquares();
        avoidCollisionWithTriangles();

        int epsilonX = epsilon.xPos;
        int epsilonY = epsilon.yPos;

        // Calculate direction towards epsilon
        int dx = epsilonX - squareEnemy.getxPos();
        int dy = epsilonY - squareEnemy.getyPos();

        // Normalize the direction vector
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        if (magnitude < 2) { // Adjust the threshold as needed

            return;
        }
        double normalizedDX = dx / magnitude;
        double normalizedDY = dy / magnitude;

        if (!squareEnemy.impactedWithEpsilon) {
            squareEnemy.setSpeed(2);
        }
        squareEnemy.xPos += normalizedDX * squareEnemy.getSpeed();
        squareEnemy.yPos += normalizedDY * squareEnemy.getSpeed();
    }


    public boolean checkVerticesHitEpsilon(EpsilonController epsilonController){
        Rectangle Square = new Rectangle(squareEnemy.getxPos(),squareEnemy.getyPos(),squareEnemy.width,squareEnemy.height);
        Rectangle epsilonRect = new Rectangle(epsilonController.getEpsilon().xPos,epsilonController.getEpsilon().yPos,epsilonController.getEpsilon().width,epsilonController.getEpsilon().height);
        if (Square.intersects(epsilonRect)){

            epsilonController.handleImpact(squareEnemy);
            epsilonController.getEpsilon().HP -=6;
            if (!epsilonController.getEpsilon().dead){
                squareEnemy.getGameFrame().gamePanel.playSE(squareEnemy.getGameFrame().gamePanel.getSound().damageSE);
            }

            if (epsilonController.getEpsilon().HP <= 0){
                epsilonController.getEpsilon().dead = true;
            }
            return true;
        }
        return false;
    }


    public void moveBack(Epsilon epsilon) {
        int moveDistance = 10; // Adjust this value as needed

        // Calculate the direction opposite to the epsilon's direction
        int oppositeX = squareEnemy.getxPos() + (squareEnemy.getxPos() - epsilon.xPos);
        int oppositeY = squareEnemy.getyPos() + (squareEnemy.getyPos() - epsilon.yPos);

        // Move the square enemy back for a brief period
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count <= 1) {
                    squareEnemy.xPos += ((oppositeX - squareEnemy.xPos)) * 2; // Move 1/10th of the distance each iteration
                    squareEnemy.yPos += ((oppositeY - squareEnemy.yPos)) * 2 ;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 100); // Adjust the delay and period as needed (0 ms delay, 100 ms period)
    }


    //when a shot hits a square the impact affects other squares'movement:
    public void moveOtherSquaresBack(Epsilon epsilon) {


        // Calculate the direction opposite to the epsilon's direction
        int oppositeX = squareEnemy.getxPos() + (squareEnemy.getxPos() - epsilon.xPos);
        int oppositeY = squareEnemy.getyPos() + (squareEnemy.getyPos() - epsilon.yPos);

        // Move the square enemy back for a brief period
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;
            @Override
            public void run() {
                count++;
                if (count <= 1) {
                    squareEnemy.xPos += (oppositeX - squareEnemy.getxPos()) / 12; // Move 1/10th of the distance each iteration
                    squareEnemy.yPos += (oppositeY - squareEnemy.getyPos()) / 12;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 100); // Adjust the delay and period as needed (0 ms delay, 100 ms period)
    }





    public void avoidCollisionWithOtherSquares() {
        for (SquareEnemy otherSquare : GamePanel.squares) {
            if (otherSquare != squareEnemy ) {
                Rectangle squareRec = new Rectangle(squareEnemy.getxPos(),squareEnemy.getyPos(),squareEnemy.width,squareEnemy.height);
                Rectangle otherRec = new Rectangle(otherSquare.xPos,otherSquare.yPos,otherSquare.width,otherSquare.height);

                if (squareRec.intersects(otherRec)){
                    // Move away from the other square
                    int dx = squareEnemy.xPos - otherSquare.xPos;
                    int dy = squareEnemy.yPos - otherSquare.yPos;
                    double magnitude = Math.sqrt(dx * dx + dy * dy);
                    double normalizedDX = dx / magnitude;
                    double normalizedDY = dy / magnitude;
                    int avoidanceDistance = 15; // Adjust the avoidance distance as needed
                    squareEnemy.xPos += normalizedDX * avoidanceDistance;
                    squareEnemy.yPos += normalizedDY * avoidanceDistance;
                    otherSquare.xPos +=  normalizedDX * -avoidanceDistance;
                    otherSquare.yPos += normalizedDX * -avoidanceDistance;
                    moveOtherSquaresBack(squareEnemy.getGameFrame().gamePanel.getEpsilonController().getEpsilon());
                }
            }
        }
    }

    public void avoidCollisionWithTriangles() {
        Rectangle squareRec = new Rectangle(squareEnemy.getxPos(),squareEnemy.getyPos(),squareEnemy.width,squareEnemy.height);
        for (int i = 0; i < GamePanel.triangles.size(); i++) {
            TrigorathEnemy trigorathEnemy = GamePanel.triangles.get(i);
            Polygon triangle = new Polygon(trigorathEnemy.xPoints, trigorathEnemy.yPoints, trigorathEnemy.nPoints);
            if (squareRec.intersects(triangle.getBounds2D())){
                // Move away from the triangle
                int dx = squareEnemy.xPos - triangle.xpoints[1];
                int dy = squareEnemy.yPos - triangle.ypoints[1];
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double normalizedDX = dx / magnitude;
                double normalizedDY = dy / magnitude;
                int avoidanceDistance = 30; // Adjust the avoidance distance as needed
                squareEnemy.xPos += normalizedDX * avoidanceDistance;
                squareEnemy.yPos += normalizedDY * avoidanceDistance;


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
