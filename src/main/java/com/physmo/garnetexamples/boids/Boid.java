package com.physmo.garnetexamples.boids;

import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

public class Boid {

    public double x, y, dx, dy, a;

    public void tick(double t) {
        double power = 0.1;
        double friction = 0.01;

        dx += Math.sin(Math.toRadians(a)) * power * t;
        dy += Math.cos(Math.toRadians(a)) * power * t;

        dx -= (dx * friction * t);
        dy -= (dy * friction * t);

        x += dx * t;
        y += dy * t;

        if (a > 360) a -= 360;
        if (a < 0) a += 360;
    }

    public void draw(SpriteBatch sb) {
        //Sprite2D spr = new Sprite2D((int)x,(int)y,16,16,0,0);
        //spr.addAngle((float)a);
        //sb.add(spr);
//        sb.add(Sprite2D.build(30, 30, 16, 16,
//                16, 0).addAngle((int)a).addColor(x/100.0,y/100.0,(x*y)/100.0));

        Sprite2D spr = Sprite2D.build((int) (x), (int) (y), 16, 16, 16, 0, 16, 16);
        spr.addAngle((float) -(a) + 180);


        spr.addColor((float) (x / 600.0), (float) (y / 400.0), (float) ((x * y) / 1500.0));

        sb.add(spr);
    }
}
