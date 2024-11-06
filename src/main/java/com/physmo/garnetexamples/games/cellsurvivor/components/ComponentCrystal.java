package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.simplecollision.Collidable;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnet.toolkit.simplecollision.CollisionSystem;

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
    public void draw(Graphics g) {
        int x = (int) parent.getTransform().x;
        int y = (int) parent.getTransform().y;

        spriteHelper.drawSpriteInMap(x - 8, y - 8, 7, 1);
    }

    public void requestKill() {
        killMe = true;
    }
}
