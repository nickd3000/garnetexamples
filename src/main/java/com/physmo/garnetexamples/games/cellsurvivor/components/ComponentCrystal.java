package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.simplecollision.Collidable;
import com.physmo.garnettoolkit.simplecollision.ColliderComponent;
import com.physmo.garnettoolkit.simplecollision.CollisionSystem;

public class ComponentCrystal extends Component {
    SpriteHelper spriteHelper;
    boolean killMe = false;

    @Override
    public void init() {
        spriteHelper = parent.getContext().getComponent(SpriteHelper.class);

    }

    @Override
    public void tick(double t) {
        if (killMe) {
            CollisionSystem collisionSystem = parent.getContext().getObjectByType(CollisionSystem.class);
            Collidable collidable = parent.getComponent(ColliderComponent.class);
            collisionSystem.removeCollidable(collidable);
            parent.destroy();
        }
    }

    @Override
    public void draw() {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        spriteHelper.drawSpriteInMap(x, y, 0, 5);
    }

    public void requestKill() {
        killMe = true;
    }
}
