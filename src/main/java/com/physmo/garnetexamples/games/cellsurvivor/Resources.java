package com.physmo.garnetexamples.games.cellsurvivor;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.graphics.Texture;
import com.physmo.garnet.graphics.TileSheet;

public class Resources {
    String spritesFileName = "prototypeArt.png";
    Texture spritesTexture;
    TileSheet spritesTilesheet;
    Graphics graphics;

    public void init(Graphics graphics) {
        this.graphics = graphics;
        spritesTexture = Texture.loadTexture(spritesFileName);
        spritesTexture.setFilter(false); // Show what smoothed textures look like
        graphics.addTexture(spritesTexture);
        spritesTilesheet = new TileSheet(spritesTexture, 16, 16);

    }

    public Texture getSpritesTexture() {
        return spritesTexture;
    }

    public TileSheet getSpritesTilesheet() {
        return spritesTilesheet;
    }


}
