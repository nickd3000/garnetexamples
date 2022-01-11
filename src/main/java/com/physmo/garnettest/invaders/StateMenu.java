package com.physmo.garnettest.invaders;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.regularfont.RegularFont;

public class StateMenu extends GameState {

    public static final String fontName = "/12x12Font.png";
    RegularFont regularFont;

//    BMFFont bmfFont;
//    Texture bmfFontTexture;

    Resources resources;

    public StateMenu(Garnet garnet, String name) {
        super(garnet, name);
    }

    @Override
    public void init(Garnet garnet) {
        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);

//        bmfFontTexture = Texture.loadTexture(Utils.getPathForResource(this, "/ptmono16_0.png"));
//        bmfFont = new BMFFont();
//        String pathForResource = Utils.getPathForResource(this, "/ptmono16.fnt");
//        try {
//            bmfFont.init(pathForResource);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        resources = (Resources) garnet.getGlobalObject("Resources");
        if (resources == null) System.out.println("resources is null");
    }

    @Override
    public void tick(double delta) {
        if (garnet.getInput().isPressed(Input.VirtualButton.FIRE1)) {
            garnet.switchActiveState("main");
        }
    }

    @Override
    public void draw() {
        regularFont.clearSpriteBatch();
        regularFont.drawText("Invaders", 20, 100, 3);
        regularFont.render();

        // temp bmf font stuff
        resources.bmfFontTexture.bind();
        resources.bmfFont.drawString(resources.bmfFontTexture, "Hello World 123 ", 20, 20, 2);

        ////////
    }
}
