package Controller;

import Model.Collectable;
import Model.Shot;
import Model.SquareEnemy;
import Model.TrigorathEnemy;
import View.GameFrame;
import View.GamePanel;

import java.awt.*;

public class ShotController {
    private final Shot shot;
    private final GamePanel gamePanel;

    public ShotController(Shot shot, GamePanel gamePanel) {
        this.shot = shot;
        this.gamePanel = gamePanel;
    }

    public void moveShot() {
        shot.move();
        checkCollisionWithFrame();
    }

    public void checkCollisionWithFrame(){
        {
            if (shot.getxPos() + shot.width >= gamePanel.getX() +gamePanel.getWidth()) {
                GameFrame.collidRightWithShot = true;
                GameFrame.collidLeftWithShot = false;

            } else if (shot.getxPos() <= gamePanel.getX()) {
                GameFrame.collidRightWithShot = false;
                GameFrame.collidLeftWithShot = true;
            } else {
                GameFrame.collidRightWithShot = false;
                GameFrame.collidLeftWithShot = false;
            }
        }

        {
            if (shot.getyPos() <= gamePanel.getY()){
                GameFrame.collidUpWithShot = true;
                GameFrame.collidDownWithShot = false;
            }
            else if (shot.getyPos() + shot.height >= gamePanel.getY() + gamePanel.getHeight()){
                GameFrame.collidUpWithShot = false;
                GameFrame.collidDownWithShot = true;
            }
            else {
                GameFrame.collidUpWithShot = false;
                GameFrame.collidDownWithShot = false;
            }
        }

    }

    public void collidWithTriangle(TrigorathEnemy triangleEnemy) {
        Rectangle bullet = new Rectangle(shot.getxPos(),shot.getyPos(), shot.width, shot.height);
        Polygon triangle = new Polygon(triangleEnemy.xPoints,triangleEnemy.yPoints, triangleEnemy.nPoints);

        if (bullet.intersects(triangle.getBounds2D())) {
            triangleEnemy.HP -= 5;
            gamePanel.playSE(gamePanel.getSound().damageSE);

            if (triangleEnemy.HP <= 0) {
                gamePanel.playSE(gamePanel.getSound().killEnemySE);
                triangleEnemy.xDeath = triangleEnemy.xP2;
                triangleEnemy.yDeath = triangleEnemy.yP2;
                new Collectable(triangleEnemy);
                GamePanel.triangles.remove(triangleEnemy);
            }

            // Apply impact effect to the hit triangle
            TrigorathEnemy.mainImpact = true;
            triangleEnemy.handleImpact(shot.getEpsilon());
            TrigorathEnemy.mainImpact = false;
            // Apply impact effect to other triangle enemies
            for (TrigorathEnemy otherEnemy : GamePanel.triangles) {
                if (otherEnemy != triangleEnemy) {
                    otherEnemy.handleImpact(shot.getEpsilon());
                }
            }
            TrigorathEnemy.mainImpact = true;
            for (SquareEnemy otherEnemy : GamePanel.squares) {
                SquareEnemyController squareEnemyController = new SquareEnemyController(otherEnemy);
                squareEnemyController.moveOtherSquaresBack(shot.getEpsilon());
            }
            shot.getEpsilon().shots.remove(this);
        }
    }

    public void collidWithSquare(SquareEnemy squareEnemy) {
        Rectangle bullet = new Rectangle(shot.getxPos(), shot.getyPos(), shot.width, shot.height);
        Rectangle squareRect = new Rectangle(squareEnemy.getxPos(), squareEnemy.getyPos(), squareEnemy.width, squareEnemy.height);
        if (bullet.intersects(squareRect)) {
            squareEnemy.HP -= 5;
            gamePanel.playSE(gamePanel.getSound().damageSE);
            if (squareEnemy.HP <= 0) {
                gamePanel.playSE(gamePanel.getSound().killEnemySE);
                squareEnemy.setxDeath(squareEnemy.getxPos());
                squareEnemy.setyDeath(squareEnemy.getyPos());
                new Collectable(squareEnemy);
                GamePanel.squares.remove(squareEnemy);
            }
            shot.getEpsilon().shots.remove(this);
            SquareEnemyController squareEnemyController = new SquareEnemyController(squareEnemy);
            squareEnemyController.moveBack(shot.getEpsilon());

            for (SquareEnemy otherEnemy : GamePanel.squares) {
                if (otherEnemy != squareEnemy) {
                    SquareEnemyController otherEnemyController = new SquareEnemyController(otherEnemy);
                    otherEnemyController.moveOtherSquaresBack(shot.getEpsilon());
                }
            }

            TrigorathEnemy.mainImpact = false;
            for (TrigorathEnemy otherEnemy : GamePanel.triangles) {
                otherEnemy.handleImpact(shot.getEpsilon());
            }
            TrigorathEnemy.mainImpact = true;
        }
    }

}