package com.physmo.garnettest.entitytest;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.entity.EntityGroup;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class ContainerEntityTest extends GameState {

    private static String fileName = "/Users/nick/Dev/java/garnettest/src/main/resources/space.PNG";
    private static float angle = 0;
    double movementX;
    int numEntites = 1000;
    private Texture texture;
    private SpriteBatch spriteBatch;
    private EntityGroup entityGroup;

    public static void main(String[] args) {
        Garnet garnet = new Garnet(new ContainerEntityTest(), 640, 480);
        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        texture = Texture.loadTexture(fileName);
        spriteBatch = new SpriteBatch(texture);

        System.out.println("adding keyboard callback from game container");

        garnet.addKeyboardCallback((key, scancode, action, mods) -> {
            System.out.println("keyboard handler" + scancode + "  " + action);
        });

        entityGroup = new EntityGroup();
        for (int i = 0; i < numEntites; i++) {
            Entity e1 = new Entity("test", this);
            e1.position.set(100, 100, 0);
            e1.velocity.set((Math.random() - 0.5) * 100.0, (Math.random() - 0.5) * 100.0, 0);

            e1.addComponent(new TestMovementComponent());
            e1.addComponent(new WallBounceComponent());
            e1.addComponent(new FrictionComponent());
            entityGroup.add(e1);
            BasicEntityDrawer basicEntityDrawer = new BasicEntityDrawer();
            e1.addEntityDrawer(basicEntityDrawer);
        }
    }

    @Override
    public void tick(double delta) {
        entityGroup.tickAll(delta);
    }

    @Override
    public void draw() {

        //drawTestSpriteBuilder();
        entityGroup.drawAll();
        spriteBatch.render();
        spriteBatch.clear();

    }

    private void drawTestSpriteBuilder() {
        int space = 30;
        int x = 20;

        // Moving
        spriteBatch.add(Sprite2D.build(20 + (int) (movementX * 300), 20 + 40, 16, 16, 0, 0, 16, 16));

        // Unscaled
        spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16));
        x += space;

        // Coloured
        spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16)
                .addColor(1.0f, 0.5f, 0.25f));
        x += space;

        // Rotated
        spriteBatch.add(Sprite2D.build(x, 20, 16, 16, 0, 0, 16, 16)
                .addAngle(angle));
        x += space;

        // Scaled
        spriteBatch.add(Sprite2D.build(x, 20, 16 * 2, 16 * 2, 0, 0, 16, 16));
        x += space * 2;

        // Tile setter
        spriteBatch.add(Sprite2D.build(x, 20, 16 * 5, 16 * 5)
                .setTile(6, 0, 16)
                .addColor(0.25f, 0.5f, 1.0f));

    }


}
