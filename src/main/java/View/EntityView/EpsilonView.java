package View.EntityView;

import View.GamePanel;
import Model.Epsilon;
import Model.Shot;

import java.awt.*;

public class EpsilonView {
    private Epsilon epsilon;

    public EpsilonView(Epsilon epsilon) {
        this.epsilon = epsilon;
    }

    public void paint(Graphics g) {
        if (!epsilon.isDead()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(8));
            g.fillOval(epsilon.getxPos(), epsilon.getyPos(), (int) epsilon.getWidth(), (int) epsilon.getHeight());
            for (Shot shot : epsilon.getShots()) {
                shot.paint(g);
            }
        } else {
            GamePanel.gameOver = true;
            GamePanel.numberOfGameOver++;
        }
    }
}
