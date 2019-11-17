import com.physmo.garnet.GameContainer;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Texture;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnet.spritebatch.Sprite2D;
import com.physmo.garnet.spritebatch.SpriteBatch;

public class ContainerSpriteSample implements GameContainer {

    public static final String fontFileName = "/Users/nick/Dev/java/garnettest/src/main/resources/8x8Font.png";
    static String fileName = "/Users/nick/Dev/java/garnettest/src/main/resources/space.PNG";
    static int scrollX = 0;
    static float angle = 0;
    Texture texture;
    SpriteBatch spriteBatch;

    RegularFont regularFont;

    @Override
    public void init(Garnet garnet) {
        regularFont = new RegularFont(fontFileName,8,8);

        texture = Texture.loadTexture(fileName);
        spriteBatch = new SpriteBatch(texture);

        System.out.println("adding keyboard callback from game container");

        garnet.addKeyboardCallback((key, scancode, action, mods) -> {
            System.out.println("keyboard handler" + scancode + "  " + action);
        });

//        System.out.println("index of a:"+(int)((char)'a'));
//        System.out.println("index of A:"+(int)((char)'A'));
//        System.out.println("index of 1:"+(int)((char)'1'));


    }

    @Override
    public void tick() {

    }

    @Override
    public void draw() {

//        spriteBatch.add(new Sprite2D(10, 10, 16, 16, 16, 0, 16, 16, 0));
//        spriteBatch.add(new Sprite2D(10 + 40, 10, 32, 32, 16, 0, 16, 16, 0));
//        spriteBatch.add(new Sprite2D(10, 60, 16, 16, 16, 0, 16, 16, angle));
//        spriteBatch.add(new Sprite2D(10 + 40, 60, 32, 32, 16, 0, 16, 16, angle * 3));

        angle += 1f;

        scrollX += 1;

        //drawTestSpinningGrid(20, 30, 20);
        drawTestSpriteBuilder();
        drawTestRegularFont();

        spriteBatch.render();
        spriteBatch.clear();

        //regularFont.renderChar((char)(Math.random()*250.0),20,20,2);
        regularFont.renderChar('A',20,20,1);
        regularFont.drawText("Abcdefg 12345", 20,60, 2);
        regularFont.drawText("The quick brown fox", 20,80,2);
        regularFont.drawText("zZxXyY678", 20,100,4);

        regularFont.render();
    }

    public void drawTestSpriteBuilder() {
        spriteBatch.add(Sprite2D.build(10, 60, 16, 16, 0, 0, 16, 16));
        spriteBatch.add(Sprite2D.build(10, 100, 16, 16, 0, 0, 16, 16).addAngle(angle));
    }

    public void drawTestRegularFont() {
        String testMessage = "Font test 1234!";
        regularFont.drawText(testMessage,50,50,2);
    }


    public void drawTestSpinningGrid(float span, int columns, int rows) {
        //glColor3f(1.0f,0.0f,0.0f);

        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {

                //glColor3f((float)Math.sin(x),(float)Math.sin(y),(float)Math.sin(x+y));

//                spriteBatch.add(new Sprite2D(
//                        (int)(x*span), (int)(y*span), 16, 16,
//                        16, 0, 16, 16,
//                        angle ));

                spriteBatch.add(Sprite2D.build((int) (x * span), (int) (y * span), 16, 16,
                        16, 0, 16, 16).addAngle(angle).addColor(x / 10.0, y / 10.0, (x * y) / 10.0));


            }
        }
    }

}
