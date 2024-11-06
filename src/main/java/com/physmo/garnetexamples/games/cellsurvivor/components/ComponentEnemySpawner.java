package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.structure.Rect;
import com.physmo.garnet.structure.Vector3;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.EntityFactory;

import java.util.List;
import java.util.Random;

public class ComponentEnemySpawner extends Component {
    SpriteHelper spriteHelper;
    Garnet garnet;
    CollisionSystem collisionSystem;
    ComponentLevelMap componentLevelMap;
    Random random = new Random();
    double spawnMargin = 32;
    double despawnMargin = 64;
    int TOP = 0;
    int RIGHT = 1;
    int BOTTOM = 2;
    int LEFT = 3;

    @Override
    public void init() {
        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        componentLevelMap = parent.getContext().getComponent(ComponentLevelMap.class);
    }

    @Override
    public void tick(double t) {

        List<GameObject> enemyList = parent.getContext().getObjectsByTag(Constants.TAG_ENEMY);

        if (Math.random() < 0.3) {
            int numEnemies = enemyList.size();
            //EntityFactory.addEnemy(context, collisionSystem, random.nextInt(400), random.nextInt(400));

            if (numEnemies < 100) {
                enemySpawn();
            }
        }

        enemyDespawn(enemyList);

        garnet.getDebugDrawer().setUserString("enemies: ", String.valueOf(enemyList.size()));
    }

    @Override
    public void draw(Graphics g) {

        Rect vme = componentLevelMap.getVisibleMapExtents();
        spriteHelper.drawSpriteInMap((int) vme.x, (int) vme.y, 3, 1);
        spriteHelper.drawSpriteInMap((int) (vme.x + vme.w), (int) vme.y, 3, 1);
        spriteHelper.drawSpriteInMap((int) vme.x, (int) (vme.y + vme.h), 3, 1);
        spriteHelper.drawSpriteInMap((int) (vme.x + vme.w), (int) (vme.y + vme.h), 3, 1);

    }

    public void enemySpawn() {
        Rect visibleMapExtents = componentLevelMap.getVisibleMapExtents();

        garnet.getDebugDrawer().setUserString("extents:",
                String.format("%f %f %f %f", visibleMapExtents.x, visibleMapExtents.y, visibleMapExtents.w, visibleMapExtents.h));

        double[] pointAlongEdge = getPointAlongEdge(visibleMapExtents, random.nextInt(4));

        EntityFactory.addEnemy(parent.getContext(), collisionSystem, (int) pointAlongEdge[0], (int) pointAlongEdge[1]);

    }

    public void enemyDespawn(List<GameObject> enemyList) {
        Rect visibleMapExtents = componentLevelMap.getVisibleMapExtents();

        double margin = despawnMargin;
        for (GameObject gameObject : enemyList) {
            Vector3 transform = gameObject.getTransform();
            if (outsideVisibleMap(transform, visibleMapExtents)) {
                ColliderComponent colliderComponent = gameObject.getComponent(ColliderComponent.class);
                collisionSystem.removeCollidable(colliderComponent);
                gameObject.destroy();
            }
        }

    }

    public boolean outsideVisibleMap(Vector3 pos, Rect rect) {
        double margin = despawnMargin;
        double left = rect.x - margin;
        double right = rect.x + rect.w + margin;
        double top = rect.y - margin;
        double bottom = rect.y + rect.h + margin;
        if (pos.x < left) return true;
        if (pos.x > right) return true;
        if (pos.y < top) return true;
        return pos.y > bottom;
    }

    public double[] getPointAlongEdge(Rect rect, int edge) {

        // top right bottom left
        double x = 0;
        double y = 0;
        if (edge == 0 || edge == 2) {
            x = rect.x + (Math.random() * rect.w);
            y = rect.y;
        }
        if (edge == 1 || edge == 3) {
            y = rect.y + (Math.random() * rect.h);
            x = rect.x;
        }

        if (edge == BOTTOM) {
            y = rect.y + rect.h;
        }
        if (edge == RIGHT) {
            x = rect.x + rect.w;
        }

        if (edge == TOP) {
            y -= spawnMargin;
        }
        if (edge == RIGHT) {
            x += spawnMargin;
        }
        if (edge == BOTTOM) {
            y += spawnMargin;
        }
        if (edge == LEFT) {
            x -= spawnMargin;
        }

        return new double[]{x, y};
    }


}
