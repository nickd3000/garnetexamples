package com.physmo.garnettest.invaders;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.regularfont.RegularFont;

public class StateMenu extends GameState {

    public static final String fontName = "/12x12Font.png";
    RegularFont regularFont;

    @Override
    public void init(Garnet garnet) {
        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);
    }

    @Override
    public void tick(double delta) {
        if (Input.isPressed(Input.VirtualButton.FIRE1)) {
            garnet.switchActiveState("main");
        }
    }

    @Override
    public void draw() {
        regularFont.clearSpriteBatch();
        regularFont.drawText("Invaders", 20, 100, 3);
        regularFont.render();
    }
}
