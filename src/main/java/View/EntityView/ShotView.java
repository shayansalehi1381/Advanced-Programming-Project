package View.EntityView;

import Model.Shot;

import java.awt.*;

public class ShotView {
    private final Shot shot;

    public ShotView(Shot shot) {
        this.shot = shot;
    }

    public void paint(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval(shot.getxPos(), shot.getyPos(), shot.width, shot.height);
    }
}
