package com.physmo.garnettest.invaders.renderers;

import com.physmo.garnet.entity.RenderComponent;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;
import com.physmo.garnettest.invaders.EnemyType;
import com.physmo.garnettest.invaders.components.ComponentEnemy;

public class RenderComponentEnemy extends RenderComponent {
    SpriteBatch spriteBatch;

    public RenderComponentEnemy(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
    }

    @Override
    public void draw() {
        Sprite2D spr = Sprite2D.build(
                (int) (parent.position.x) - 8,
                (int) (parent.position.y) - 8,
                16, 16, 32, 32, 16, 16);

        ComponentEnemy component = parent.getComponent(ComponentEnemy.class);

        if (component.enemyType == EnemyType.basic) spr.addColor(0, 1, 0);
        if (component.enemyType == EnemyType.armoured) spr.addColor(0.5f, 0.6f, 0.7f);
        if (component.enemyType == EnemyType.shooter) spr.addColor(1, 0.5f, 0);
        spriteBatch.add(spr);


    }
}
