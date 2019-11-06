package old;

import com.physmo.garnet.GameContainer;
import com.physmo.garnet.Texture;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

import static org.lwjgl.opengl.GL11.glColor3f;

public class TestGameContainer implements GameContainer {


    static String fileName = "/Users/nick/Dev/java/garnettest/src/main/resources/space.PNG";
    Texture texture;
    SpriteBatch spriteBatch;
    static int scrollX = 0;

    @Override
    public void init() {

        texture = Texture.loadTexture(fileName);
        spriteBatch = new SpriteBatch(texture);
    }

    @Override
    public void tick() {

    }

    static float angle = 0;

    @Override
    public void draw() {

//        spriteBatch.add(new Sprite2D(10, 10, 16, 16, 16, 0, 16, 16, 0));
//        spriteBatch.add(new Sprite2D(10 + 40, 10, 32, 32, 16, 0, 16, 16, 0));
//        spriteBatch.add(new Sprite2D(10, 60, 16, 16, 16, 0, 16, 16, angle));
//        spriteBatch.add(new Sprite2D(10 + 40, 60, 32, 32, 16, 0, 16, 16, angle * 3));

        angle += 1f;

        scrollX += 1;

        drawTestSpinningGrid(20,30,20);
        drawTestSpriteBuilder();

        spriteBatch.render();
        spriteBatch.clear();
    }

    public void drawTestSpriteBuilder() {
        spriteBatch.add(Sprite2D.build(10,60,16,16,0,0));
        spriteBatch.add(Sprite2D.build(10,100,16,16,0,0).addAngle(angle));
    }

    public void drawTestSpinningGrid(float span, int columns, int rows) {
        //glColor3f(1.0f,0.0f,0.0f);

        for (int y=0;y<columns;y++) {
            for (int x = 0; x < rows; x++) {

                //glColor3f((float)Math.sin(x),(float)Math.sin(y),(float)Math.sin(x+y));

//                spriteBatch.add(new Sprite2D(
//                        (int)(x*span), (int)(y*span), 16, 16,
//                        16, 0, 16, 16,
//                        angle ));

                spriteBatch.add(Sprite2D.build((int)(x*span), (int)(y*span), 16, 16,
                        16, 0).addAngle(angle).addColor(x/10.0,y/10.0,(x*y)/10.0));


            }
        }
    }

}
