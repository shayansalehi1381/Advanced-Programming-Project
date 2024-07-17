package Controller;


import Model.Collectable;

import java.util.ArrayList;
import java.util.List;

public class CollectableController {

    public static List<Collectable> collectables;

    public CollectableController() {
        collectables = new ArrayList<>();
    }

    public void addCollectable(Collectable collectable) {
        collectables.add(collectable);
    }

    public void removeCollectable(Collectable collectable) {
        collectables.remove(collectable);
    }



    public List<Collectable> getCollectables() {
        return collectables;
    }
}