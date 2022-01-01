package com.physmo.garnettest.invaders;

import com.physmo.garnet.GameState;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.Utils;
import com.physmo.garnet.input.Input;
import com.physmo.garnet.regularfont.RegularFont;

public class StatePause extends GameState {

    public static final String fontName = "/12x12Font.png";
    RegularFont regularFont;

    double initialCooldown;

    public StatePause(Garnet garnet, String name) {
        super(garnet, name);
    }

    @Override
    public void init(Garnet garnet) {
        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);
        initialCooldown=0.1;
    }

    @Override
    public void tick(double delta) {
        if (initialCooldown>0) initialCooldown-=delta;

        //if (initialCooldown<=0) {
            if (garnet.getInput().isFirstPress(Input.VirtualButton.MENU)) {
                garnet.popSubState("pause");
                //garnet.switchActiveState("main");
            }
        //}
    }

    @Override
    public void draw() {
        regularFont.clearSpriteBatch();
        regularFont.drawText("PAUSED", 20, 100, 3);
        regularFont.render();
    }
}
