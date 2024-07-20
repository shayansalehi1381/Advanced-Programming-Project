package View.EntityView.EnemyView;

import Model.SquareEnemy;

import java.awt.*;

public class SquareEnemyView extends Rectangle {

    public SquareEnemy squareEnemy;

    public SquareEnemyView(SquareEnemy squareEnemy){
        this.squareEnemy = squareEnemy;
    }


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawRect(squareEnemy.getxPos(),squareEnemy.getyPos(),squareEnemy.width,squareEnemy.height);
    }
}
