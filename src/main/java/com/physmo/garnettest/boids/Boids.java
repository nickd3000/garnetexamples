package com.physmo.garnettest.boids;

import com.physmo.garnet.GameContainer;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.spritebatch.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Boids implements GameContainer {
    static String fileName = "/Users/nick/Dev/java/garnettest/src/main/resources/space.PNG";
    double width = 640;
    double height = 480;
    int numBoids = 30;
    double startSpeed = 1;
    List<Boid> boids = new ArrayList<>();
    Texture texture;
    SpriteBatch spriteBatch;

    private static void doIt(Boid b1, Boid b2) {
        double dist = dist(b1, b2);
        double nearFar = 30;
        double tooFar = 200;
        double blend1 = 0.1;
        double blend2 = 0.1;

        if (dist > tooFar) {
            return;
        } else if (dist < nearFar) {
            b1.a = (b1.a * (1.0 - blend1)) + (b2.a * (blend1));
        } else {
            double a2 = getAngleFromVector(b1.x - b2.x, b1.y - b2.y);
            b1.a = (b1.a * (1.0 - blend2)) + (a2 * (blend2));
        }

    }

    private static double dist(Boid b1, Boid b2) {
        double dist;
        double dx = b1.x - b2.x;
        double dy = b1.y - b2.y;
        dist = Math.sqrt((dx * dx) + (dy * dy));
        return dist;
    }

    public static float getAngleFromVector(double dx, double dy) {
        if (dx == 0 && dy == 0) return 0;
        double dist = Math.sqrt((dx * dx) + (dy * dy));
        //Vector2f vv = new Vector2f(v);
        //vv = vv.normalise();
        dx /= dist;
        dy /= dist;
        float angle = (float) Math.atan2(dx, dy);
        angle = (float) Math.toDegrees(angle);
        if (dx < 0)
            angle = 360 + angle;
        angle = 360 - angle;
        return angle;
    }

    @Override
    public void init(Garnet garnet) {
        texture = Texture.loadTexture(fileName);
        spriteBatch = new SpriteBatch(texture);

        for (int i = 0; i < numBoids; i++) {
            Boid b = new Boid();
            b.x = Math.random() * width;
            b.y = Math.random() * height;
            b.a = Math.random() * 360;
// 0 = down?
            b.dx = Math.sin(Math.toRadians(b.a));
            b.dy = Math.cos(Math.toRadians(b.a));
            boids.add(b);
        }

    }

    @Override
    public void tick() {
        for (Boid b : boids) {
            b.tick(0.4);
            constrainBoid(b);
        }

        flock();
    }

    @Override
    public void draw() {
        for (Boid b : boids) {
            b.draw(spriteBatch);
        }

        spriteBatch.render();
        spriteBatch.clear();
    }

    private void constrainBoid(Boid b) {
        double pad = 50;
        if (b.x < -pad) b.x = width + pad;
        if (b.x > width + pad) b.x = -pad;
        if (b.y < -pad) b.y = height + pad;
        if (b.y > height + pad) b.y = -pad;
    }

    private void flock() {
        for (Boid b1 : boids) {
            for (Boid b2 : boids) {
                if (b1 == b2) continue;
                if (Math.random() > 0.1) continue;
                doIt(b1, b2);
            }
        }
    }
}
