package com.physmo.garnetexamples.games.cellsurvivor;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.Texture;
import com.physmo.garnet.graphics.TileSheet;
import com.physmo.garnet.text.RegularFont;

public class Resources {
    public static final String regularFontImagePath = "regularfonts/12x12Font.png";
    String spritesFileName = "cellsurvivor.png";
    Texture spritesTexture;
    TileSheet spritesTilesheet;
    Graphics graphics;
    RegularFont regularFont;


    public void init(Graphics graphics) {
        this.graphics = graphics;
        spritesTexture = Texture.loadTexture(spritesFileName);
        spritesTexture.setFilter(false); // Show what smoothed textures look like
        graphics.addTexture(spritesTexture);
        spritesTilesheet = new TileSheet(spritesTexture, 16, 16);

        // Regular font
        regularFont = new RegularFont(regularFontImagePath, 12, 12);

    }

    public Texture getSpritesTexture() {
        return spritesTexture;
    }

    public TileSheet getSpritesTilesheet() {
        return spritesTilesheet;
    }

    public RegularFont getRegularFont() {
        return regularFont;
    }


}
