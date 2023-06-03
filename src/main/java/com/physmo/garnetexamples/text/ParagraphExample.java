package com.physmo.garnetexamples.text;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.GarnetApp;
import com.physmo.garnet.ParagraphDrawer;
import com.physmo.garnet.bitmapfont.BitmapFont;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnettoolkit.color.Color;

import java.io.IOException;

// NOTE: on MacOS we need to add a vm argument: -XstartOnFirstThread
public class ParagraphExample extends GarnetApp {

    RegularFont regularFont;
    BitmapFont bitmapFont;
    ParagraphDrawer bitmapFontParagraphDrawer;
    ParagraphDrawer regularFontParagraphDrawer;

    public ParagraphExample(Garnet garnet, String name) {
        super(garnet, name);
    }

    public static void main(String[] args) {
        Garnet garnet = new Garnet(800, 600);
        GarnetApp app = new ParagraphExample(garnet, "");

        garnet.setApp(app);

        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        regularFont = new RegularFont("regularfonts/12x12Font.png", 12, 12);
        regularFont.setHorizontalPad(-5);
        garnet.getGraphics().setBackgroundColor(Color.SUNSET_BLUE.toInt());

        String bitmapFontImagePath = "bitmapfonts/SmallFont12.png";
        String bitmapFontDefinitionPath = "bitmapfonts/SmallFont12.fnt";

        try {
            bitmapFont = new BitmapFont(bitmapFontImagePath, bitmapFontDefinitionPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        bitmapFontParagraphDrawer = new ParagraphDrawer(bitmapFont);
        regularFontParagraphDrawer = new ParagraphDrawer(regularFont);
    }

    @Override
    public void tick(double delta) {
    }

    @Override
    public void draw(Graphics g) {

        String paragraphText = "The quick brown fox \n jumps over the lazy dogs. The quick brown fox jumps over the lazy dogs.";
        String paragraphText2 = "Move the mouse to change the width of this paragraph.\n The paragraph drawer returns the total height of the lines in the drawn paragraph.";

        g.setScale(2);
        g.setColor(Color.SUNSET_GREEN.toInt());
        //regularFont.drawText(garnet.getGraphics(), "Regular font", 0, 10);

        regularFontParagraphDrawer.drawParagraph(g, paragraphText, 200, 200, 0, 0);

        int[] mp = garnet.getInput().getMousePositionScaled(2);
        regularFontParagraphDrawer.drawParagraph(g, paragraphText, mp[0], 200, 0, 60);


        bitmapFontParagraphDrawer.setPadY(4);
        int ph = bitmapFontParagraphDrawer.drawParagraph(g, paragraphText2, mp[0], 200, 0, 200);
        g.drawRect(0, 200, mp[0], ph);

        g.render();
    }

}
