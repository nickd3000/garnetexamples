package com.physmo.garnettest.entitytest;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnet.entity.EntityGroup;
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

    public ContainerEntityTest(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet( 640, 480);
        garnet.addState( new ContainerEntityTest(garnet, "ContainerEntityTest"));
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
            e1.addComponent(new BasicEntityDrawer(spriteBatch));

            addEntity(e1);

        }
    }

    @Override
    public void tick(double delta) {
        entityGroup.tickAll(delta);
    }

    @Override
    public void draw() {
        spriteBatch.render(1);
        spriteBatch.clear();
    }

}
