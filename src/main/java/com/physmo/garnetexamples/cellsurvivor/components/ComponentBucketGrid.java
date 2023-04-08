package com.physmo.garnetexamples.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.GameObjectBucketGrid;
import com.physmo.garnettoolkit.SceneManager;

import java.util.List;

public class ComponentBucketGrid extends Component {

    GameObjectBucketGrid gameObjectBucketGrid;
    Garnet garnet;

    public GameObjectBucketGrid getGameObjectBucketGrid() {
        return gameObjectBucketGrid;
    }

    @Override
    public void init() {
        gameObjectBucketGrid = new GameObjectBucketGrid(16 * 3, 16 * 3);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
    }

    @Override
    public void tick(double t) {

        garnet.getDebugDrawer().setUserString("Buckets:", String.valueOf(gameObjectBucketGrid.getSize()));

        double minDist = 16;
        double force = 8;
        List<GameObject> enemies = parent.getContext().getObjectsByTag("enemy");
        for (GameObject enemy : enemies) {
            gameObjectBucketGrid.addObject(enemy);
        }

//        for (GameObject enemy : enemies) {
//            List<GameObject> nearby = gameObjectBucketGrid.getObjects((int) enemy.getTransform().x, (int) enemy.getTransform().y, 1);
//
//            for (GameObject nb : nearby) {
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
//
//        }


    }


    @Override
    public void draw() {

    }
}
