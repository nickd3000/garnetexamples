package com.physmo.garnetexamples.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.input.InputAction;
import com.physmo.garnetexamples.cellsurvivor.Constants;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.SceneManager;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;
import com.physmo.garnettoolkit.simplecollision.RelativeObject;

import java.util.ArrayList;
import java.util.List;

public class ComponentPlayer extends Component {

    Garnet garnet;

    List<RelativeObject> nearestEnemies = new ArrayList<>();
    List<RelativeObject> nearestCrystals = new ArrayList<>();
    SpriteHelper spriteHelper;
    CollisionSystem collisionSystem;

    public List<RelativeObject> getNearestEnemies() {
        return nearestEnemies;
    }

    public void setNearestEnemies(List<RelativeObject> list) {
        this.nearestEnemies = list;
    }

    public void setNearestCrystals(List<RelativeObject> list) {
        this.nearestCrystals = list;
    }

    @Override
    public void tick(double t) {
        double speed = 30;

        if (garnet.getInput().isPressed(InputAction.RIGHT)) {
            parent.getTransform().x += speed * t;
        }
        if (garnet.getInput().isPressed(InputAction.LEFT)) {
            parent.getTransform().x -= speed * t;
        }
        if (garnet.getInput().isPressed(InputAction.UP)) {
            parent.getTransform().y -= speed * t;
        }
        if (garnet.getInput().isPressed(InputAction.DOWN)) {
            parent.getTransform().y += speed * t;
        }

        if (nearestCrystals != null) {
            for (RelativeObject nearestCrystal : nearestCrystals) {
                Vector3 transform = nearestCrystal.otherObject.collisionGetGameObject().getTransform();

                transform.x -= nearestCrystal.dx * 70.1 * t;
                transform.y -= nearestCrystal.dy * 70.1 * t;
            }
        }


    }

    @Override
    public void init() {
        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);

        parent.addTag(Constants.TAG_PLAYER);

        ColliderComponent collider = parent.getComponent(ColliderComponent.class);

        collider.setCallbackEnter(target -> {
            if (target.hasTag(Constants.TAG_CRYSTAL)) {
                target.getComponent(ComponentCrystal.class).requestKill();
            }
        });
    }

    @Override
    public void draw() {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        spriteHelper.drawSpriteInMap(x, y, 2, 0);
    }
}
