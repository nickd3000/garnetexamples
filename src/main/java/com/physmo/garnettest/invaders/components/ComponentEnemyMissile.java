package com.physmo.garnettest.invaders.components;


import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.simplecollision.CollisionPacket;

public class ComponentEnemyMissile extends Component {
    double speed = 100;

    SpriteBatch spriteBatch;

    public ComponentEnemyMissile(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }


    @Override
    public void init() {
        //BoxCollider2D boxCollider2D = new BoxCollider2D();
        //boxCollider2D.setValues(parent, -2, -4, 4, 8);
    }

    @Override
    public void tick(double delta) {
        parent.getTransform().y += speed * delta;

        if (parent.getTransform().y > 480) parent.setActive(false);
    }


    public void onCollisionStart(CollisionPacket collisionPacket) {
        //System.out.println("enemy missile collision");
        //parent.setActive(false);
    }

    @Override
    public void draw() {
        spriteBatch.add(Sprite2D.build(
                (int) (parent.getTransform().x) - 8,
                (int) (parent.getTransform().y) - 8,
                16, 16, 16 * 4, 32, 16, 16));
    }
}
