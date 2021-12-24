package com.physmo.garnettest;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.regularfont.RegularFont;

public class ContainerRegularFontSample extends GameState {

    public static final String fontName = "/12x12Font.png";
    //public static final String fontName = "/8x8Font.png";

    RegularFont regularFont;

    public static void main(String[] args) {
        Garnet garnet = new Garnet(new ContainerRegularFontSample(), 640, 480);
        garnet.init();
        garnet.run();
    }

    @Override
    public void init(Garnet garnet) {
        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);
    }

    @Override
    public void tick(double delta) {
    }

    @Override
    public void draw() {
        regularFont.clearSpriteBatch();
        regularFont.renderChar('A', 20, 20, 1);
        regularFont.drawText("Abcdefg 12345", 20, 60, 2);
        regularFont.drawText("The quick brown fox", 20, 80, 2);
        regularFont.drawText("Regular font sample", 20, 100, 4);
        regularFont.render();
    }

}
