package View.EntityView;

import Controller.CollectableController;
import Model.Collectable;

import java.awt.*;

public class CollectableView extends Rectangle {
    CollectableController collectableController;

    public CollectableView(CollectableController collectableController){
        this.collectableController = collectableController;
    }

    public void paint(Graphics g){
        for (Collectable collectable:collectableController.getCollectables()){
            g.setColor(Color.white);
            g.fillOval(collectable.getXPos(), collectable.getYPos(), collectable.width, collectable.height);
        }

    }
}
