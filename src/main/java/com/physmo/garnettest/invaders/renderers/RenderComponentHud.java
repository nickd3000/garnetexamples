package com.physmo.garnettest.invaders.renderers;

import com.physmo.garnet.Utils;
import com.physmo.garnet.entity.RenderComponent;
import com.physmo.garnet.regularfont.RegularFont;
import com.physmo.garnettest.invaders.GameData;

public class RenderComponentHud extends RenderComponent {
    //SpriteBatch spriteBatch;
    public static final String fontName = "/12x12Font.png";
    RegularFont regularFont;

    @Override
    public void init() {
        String fontFileName = Utils.getPathForResource(this, fontName);
        regularFont = new RegularFont(fontFileName, 12, 12);
    }

    @Override
    public void draw() {
        GameData gameData = (GameData) parent.garnet.getGlobalObject("game_data");
        //regularFont.clearSpriteBatch();
        regularFont.drawText("Score:" + gameData.currentScore, 10, 10, 1);
        regularFont.drawText("Lives:" + gameData.lives, 10 + 250, 10, 1);
        regularFont.render();
    }
}
