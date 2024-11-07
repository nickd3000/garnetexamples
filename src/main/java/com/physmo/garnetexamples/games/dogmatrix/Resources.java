package com.physmo.garnetexamples.games.dogmatrix;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Texture;
import com.physmo.garnet.graphics.TileSheet;


public class Resources {

    public static final String spriteSheetFileName = "molePanic/dogMatrix.png";
    public Garnet garnet;
    public Texture spriteTexture;
    public TileSheet spriteTileSheet;

    public Resources(Garnet garnet) {
        this.garnet = garnet;
    }

    public Resources init() {

        spriteTexture = Texture.loadTexture(spriteSheetFileName);
        garnet.getGraphics().addTexture(spriteTexture);
        spriteTexture.setFilter(false);
        spriteTileSheet = new TileSheet(spriteTexture, 16, 16);

        return this;
    }

}
