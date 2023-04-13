package com.physmo.garnetexamples.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.GameObjectBucketGrid;
import com.physmo.garnettoolkit.SceneManager;

import java.util.List;

public class ComponentEnemySpacer extends Component {

    ComponentBucketGrid componentBucketGrid;
    Garnet garnet;

    @Override
    public void init() {
        //gameObjectBucketGrid = new GameObjectBucketGrid(16 * 3, 16 * 3);
        componentBucketGrid = parent.getContext().getComponent(ComponentBucketGrid.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
    }

    @Override
    public void tick(double t) {

//        garnet.getDebugDrawer().setUserString("Buckets:", "" + gameObjectBucketGrid.getSize());
//
        GameObjectBucketGrid gameObjectBucketGrid = componentBucketGrid.getGameObjectBucketGrid();

        double minDist = 16;
        double force = 8;
        List<GameObject> enemies = parent.getContext().getObjectsByTag("enemy");
//        for (GameObject enemy : enemies) {
//            gameObjectBucketGrid.addObject(enemy);
//        }

        for (GameObject enemy : enemies) {
            List<Object> nearby = gameObjectBucketGrid.getSurroundingObjects((int) enemy.getTransform().x, (int) enemy.getTransform().y, 1);

//            for (Object nb : nearby) {
//                if (enemy == nb) continue;
//
//                double distance = enemy.getTransform().distance(nb.getTransform());
//                if (distance > minDist) continue;
//
//                Vector3 dir = enemy.getTransform().getDirectionTo(nb.getTransform());
//
//                Vector3 scale = dir.scale(t * force);
//                enemy.getTransform().addi(scale);
//                //System.out.println();
//            }

        }


    }


    @Override
    public void draw() {

    }
}
