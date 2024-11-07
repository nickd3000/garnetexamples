package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.InputAction;
import com.physmo.garnet.structure.Vector3;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;
import com.physmo.garnet.toolkit.simplecollision.RelativeObject;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;

import java.util.ArrayList;
import java.util.List;

public class ComponentPlayer extends Component {

    Garnet garnet;

    List<RelativeObject> nearestEnemies = new ArrayList<>();
    List<RelativeObject> nearestCrystals = new ArrayList<>();
    SpriteHelper spriteHelper;
    CollisionSystem collisionSystem;
    ColliderComponent collider;
    ComponentPlayerCapabilities playerCapabilities;

    public List<RelativeObject> getNearestEnemies() {
        return nearestEnemies;
    }

    public void setNearestEnemies(List<RelativeObject> list) {
        this.nearestEnemies = list;
    }

    public void setNearestCrystals(List<RelativeObject> list) {
        this.nearestCrystals = list;
    }

    ComponentGameLogic gameLogic;

    @Override
    public void tick(double t) {
        double speed = 30;

        if (garnet.getInput().isActionKeyPressed(InputAction.RIGHT)) {
            parent.getTransform().x += speed * t;
        }
        if (garnet.getInput().isActionKeyPressed(InputAction.LEFT)) {
            parent.getTransform().x -= speed * t;
        }
        if (garnet.getInput().isActionKeyPressed(InputAction.UP)) {
            parent.getTransform().y -= speed * t;
        }
        if (garnet.getInput().isActionKeyPressed(InputAction.DOWN)) {
            parent.getTransform().y += speed * t;
        }

        if (nearestCrystals != null) {
            for (RelativeObject nearestCrystal : nearestCrystals) {
                Vector3 transform = nearestCrystal.otherObject.collisionGetGameObject().getTransform();

                transform.x -= nearestCrystal.dx * 70.1 * t;
                transform.y -= nearestCrystal.dy * 70.1 * t;
            }
        }

        garnet.getDebugDrawer().setUserString("d1 ", "");
        garnet.getDebugDrawer().setUserString("d2 ", "");
        garnet.getDebugDrawer().setUserString("d3 ", "");
        garnet.getDebugDrawer().setUserString("Player ", (int) parent.getTransform().x + ", " + (int) parent.getTransform().x);

        collider.setCollisionRegion(-8, -8, 14, 14);
    }

    @Override
    public void init() {
        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
        playerCapabilities = parent.getContext().getComponent(ComponentPlayerCapabilities.class);
        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);

        parent.addTag(Constants.TAG_PLAYER);

        collider = parent.getComponent(ColliderComponent.class);

        collider.setCallbackEnter(target -> {
            if (target.hasTag(Constants.TAG_CRYSTAL)) {
                target.getComponent(ComponentCrystal.class).requestKill();
                gameLogic.increaseXp(1);
            }
        });

        parent.getTransform().set(300, 200, 0);


    }

    @Override
    public void draw(Graphics g) {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        spriteHelper.drawSpriteInMap(x - 8, y - 8, 2, 0);
    }


}
