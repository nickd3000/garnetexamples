import com.physmo.garnet.GameContainer;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

public class ContainerSpriteSample implements GameContainer {


    private static String fileName = "/Users/nick/Dev/java/garnettest/src/main/resources/space.PNG";
    private static float angle = 0;
    private Texture texture;
    private SpriteBatch spriteBatch;

    @Override
    public void init(Garnet garnet) {


        texture = Texture.loadTexture(fileName);
        spriteBatch = new SpriteBatch(texture);

        System.out.println("adding keyboard callback from game container");

        garnet.addKeyboardCallback((key, scancode, action, mods) -> {
            System.out.println("keyboard handler" + scancode + "  " + action);
        });

    }

    @Override
    public void tick() {

    }

    @Override
    public void draw() {

        angle += 1f;

        drawTestSpriteBuilder();

        spriteBatch.render();
        spriteBatch.clear();

    }

    private void drawTestSpriteBuilder() {
        int space = 30;
        int x = 20;
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
