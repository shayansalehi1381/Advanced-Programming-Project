package View.EntityView.EnemyView;

import Model.TrigorathEnemy;

import java.awt.*;

public class TrigorathEnemyView extends Polygon {

    public TrigorathEnemy trigorathEnemy;

    public TrigorathEnemyView(TrigorathEnemy trigorathEnemy){
        this.trigorathEnemy = trigorathEnemy;
    }


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Draw the triangle outline
        g2d.setColor(Color.yellow); // Set color of the triangle's outline
        g2d.setStroke(new BasicStroke(3)); // Set the width of the outline
        g2d.drawPolygon(trigorathEnemy.xPoints,trigorathEnemy.yPoints,trigorathEnemy.nPoints);
    }
}
