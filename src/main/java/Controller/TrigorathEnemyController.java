package Controller;

import Model.Epsilon;
import Model.SquareEnemy;
import Model.TrigorathEnemy;
import View.GamePanel;

import java.awt.*;

public class TrigorathEnemyController {

    TrigorathEnemy trigorathEnemy;

    public TrigorathEnemyController(TrigorathEnemy trigorathEnemy){
        this.trigorathEnemy = trigorathEnemy;
    }


    public void moveTowardsEpsilon(Epsilon epsilon) {

        if (trigorathEnemy.getGameFrame().contains(trigorathEnemy.xP2,trigorathEnemy.yP2)){
            trigorathEnemy.inside++;
            if (trigorathEnemy.inside == 1){
                trigorathEnemy.getGameFrame().gamePanel.playSE(trigorathEnemy.getGameFrame().gamePanel.getSound().enemyInsideSE);
            }
        }

        avoidCollisionWithOtherTriangles();
        avoidCollisionWithSquares();

        int epsilonX = epsilon.xPos;
        int epsilonY = epsilon.yPos;

        // Calculate direction towards epsilon
        int dx = epsilonX - trigorathEnemy.xP2;
        int dy = epsilonY - trigorathEnemy.yP2;

        // Normalize the direction vector
        double magnitude = Math.sqrt((dx * dx) + (dy * dy));
        if (magnitude < 1) { // Adjust the threshold as needed

            return;
        }
        double normalizedDX = dx / magnitude;
        double normalizedDY = dy / magnitude;

        if (!trigorathEnemy.impactedWithEpsilon) {
            if (magnitude < 60) {
                trigorathEnemy.speed =  1;

            } else {
                trigorathEnemy.speed = 3;
            }
        }




        trigorathEnemy.xP1 += normalizedDX * trigorathEnemy.speed;
        trigorathEnemy.xP2 += normalizedDX * trigorathEnemy.speed;
        trigorathEnemy.xP3 += normalizedDX * trigorathEnemy.speed;
        trigorathEnemy.yP1 += normalizedDY * trigorathEnemy.speed;
        trigorathEnemy.yP2 += normalizedDY * trigorathEnemy.speed;
        trigorathEnemy.yP3 += normalizedDY * trigorathEnemy.speed;

        // Update the xPoints and yPoints arrays
        trigorathEnemy.xPoints[0] = trigorathEnemy.xP1;
        trigorathEnemy.xPoints[1] = trigorathEnemy.xP2;
        trigorathEnemy.xPoints[2] = trigorathEnemy.xP3;
        trigorathEnemy.yPoints[0] = trigorathEnemy.yP1;
        trigorathEnemy.yPoints[1] = trigorathEnemy.yP2;
        trigorathEnemy.yPoints[2] = trigorathEnemy.yP3;


    }


    public void handleImpact(Epsilon epsilon) {
        // Move the triangle back temporarily
        int dx = trigorathEnemy.xP2 - epsilon.xPos;
        int dy = trigorathEnemy.yP2 - epsilon.yPos;
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double normalizedDX = dx / magnitude;
        double normalizedDY = dy / magnitude;
        int impactDistance;
        if (trigorathEnemy.mainImpact){
            impactDistance = 50;
        }
        else impactDistance = 10;
        // Adjust the impact distance as needed

        //********************
        trigorathEnemy.xP1 += normalizedDX * impactDistance;
        trigorathEnemy.xP2 += normalizedDX * impactDistance;
        trigorathEnemy.xP3 += normalizedDX * impactDistance;
        trigorathEnemy.yP1 += normalizedDY * impactDistance;
        trigorathEnemy.yP2 += normalizedDY * impactDistance;
        trigorathEnemy.yP3 += normalizedDY * impactDistance;

        // Update the xPoints and yPoints arrays
        trigorathEnemy.xPoints[0] = trigorathEnemy.xP1;
        trigorathEnemy.xPoints[1] = trigorathEnemy.xP2;
        trigorathEnemy.xPoints[2] = trigorathEnemy.xP3;
        trigorathEnemy.yPoints[0] = trigorathEnemy.yP1;
        trigorathEnemy.yPoints[1] = trigorathEnemy.yP2;
        trigorathEnemy.yPoints[2] = trigorathEnemy.yP3;
    }







    public boolean checkVerticesHitEpsilon(EpsilonController epsilonController) {
        for (int i = 0; i < trigorathEnemy.nPoints; i++) {
            double distance = Math.sqrt(Math.pow(trigorathEnemy.xPoints[i] - epsilonController.getEpsilon().xPos, 2) + Math.pow(trigorathEnemy.yPoints[i] - epsilonController.getEpsilon().yPos, 2));
            // If the distance is less than epsilon, return true
            if (distance < epsilonController.getEpsilon().width) {
                epsilonController.handleImpact(trigorathEnemy);
                epsilonController.getEpsilon().HP -= 10;
                if (!epsilonController.getEpsilon().dead){
                    trigorathEnemy.getGameFrame().gamePanel.playSE(trigorathEnemy.getGameFrame().gamePanel.getSound().damageSE);
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
        Polygon triangle = new Polygon(trigorathEnemy.xPoints,trigorathEnemy.yPoints,trigorathEnemy.nPoints);
        for (TrigorathEnemy otherTriangle : GamePanel.triangles) {
            Polygon otherTrianglew = new Polygon(otherTriangle.xPoints, otherTriangle.yPoints, otherTriangle.nPoints);
            if (otherTriangle != trigorathEnemy && triangle.intersects(otherTrianglew.getBounds2D())) {
                // Move away from the other triangle

                int dx = trigorathEnemy.xP2 - otherTriangle.xP2;
                int dy = trigorathEnemy.yP2 - otherTriangle.yP2;
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double normalizedDX = dx / magnitude;
                double normalizedDY = dy / magnitude;
                int avoidanceDistance = 25; // Adjust the avoidance distance as needed
                trigorathEnemy.xP1 += normalizedDX * avoidanceDistance;
                trigorathEnemy.xP2 += normalizedDX * avoidanceDistance;
                trigorathEnemy.xP3 += normalizedDX * avoidanceDistance;
                trigorathEnemy.yP1 += normalizedDY * avoidanceDistance;
                trigorathEnemy.yP2 += normalizedDY * avoidanceDistance;
                trigorathEnemy.yP3 += normalizedDY * avoidanceDistance;



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
        Polygon triangle = new Polygon(trigorathEnemy.xPoints, trigorathEnemy.yPoints,trigorathEnemy.nPoints);
        for (SquareEnemy square : GamePanel.squares) {
            Rectangle squareRec = new Rectangle(square.xPos,square.yPos,square.width,square.height);
            if (triangle.getBounds2D().intersects(squareRec)) {

                // Move away from the square
                int dx = trigorathEnemy.xP2 - square.xPos;
                int dy = trigorathEnemy.yP2 - square.yPos;
                double magnitude = Math.sqrt(dx * dx + dy * dy);
                double normalizedDX = dx / magnitude;
                double normalizedDY = dy / magnitude;
                int avoidanceDistance = 10; // Adjust the avoidance distance as needed
                trigorathEnemy.xP1 += normalizedDX * avoidanceDistance;
                trigorathEnemy.xP2 += normalizedDX * avoidanceDistance;
                trigorathEnemy.xP3 += normalizedDX * avoidanceDistance;
                trigorathEnemy.yP1 += normalizedDY * avoidanceDistance;
                trigorathEnemy.yP2 += normalizedDY * avoidanceDistance;
                trigorathEnemy.yP3 += normalizedDY * avoidanceDistance;


                square.xPos += normalizedDX * -avoidanceDistance;
                square.yPos += normalizedDX * -avoidanceDistance;

            }
        }
    }

}
