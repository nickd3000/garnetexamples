package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.tilegrid.TileGridData;
import com.physmo.garnet.tilegrid.TileGridDrawer;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.GameObject;
import com.physmo.garnettoolkit.Rect;
import com.physmo.garnettoolkit.Vector3;
import com.physmo.garnettoolkit.scene.SceneManager;

public class ComponentLevelMap extends Component {
    TileGridData tileGridData;
    TileGridDrawer tileGridDrawer;
    int mapWidth = 100;
    int mapHeight = 100;
    double scale = 2;
    Resources resources;
    Graphics graphics;
    Garnet garnet;
    GameObject player;
    double scrollX = 0;
    double scrollY = 0;

    int windowWidth = 300;
    int windowHeight = 200;

    public ComponentLevelMap() {


    }

    @Override
    public void tick(double t) {

        Vector3 playerPos = player.getTransform();
        double dx = playerPos.x - (scrollX + (windowWidth / 2));
        double dy = playerPos.y - (scrollY + (windowHeight / 2));
        double speed = 5.0 * t;
        scrollX += dx * speed;
        scrollY += dy * speed;
    }

    public TileGridDrawer getTileGridDrawer() {
        return tileGridDrawer;
    }

    @Override
    public void init() {


        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        graphics = garnet.getGraphics();

        resources = parent.getContext().getObjectByType(Resources.class);

        tileGridData = new TileGridData(mapWidth, mapHeight);
        tileGridDrawer = new TileGridDrawer().setData(tileGridData).setWindowSize(windowWidth, windowHeight).setTileSize(16, 16).setTileSheet(resources.getSpritesTilesheet()).setScale((int) scale);

        int grass = resources.getSpritesTilesheet().getTileIndexFromCoords(1, 7);
        int flower = resources.getSpritesTilesheet().getTileIndexFromCoords(2, 7);
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (Math.random() < 0.98) tileGridData.setTileId(x, y, grass);
                else tileGridData.setTileId(x, y, flower);
            }
        }

        player = parent.getContext().getObjectByTag(Constants.TAG_PLAYER);
    }

    @Override
    public void draw() {
        tileGridDrawer.setScale(2);
        tileGridDrawer.setScroll(scrollX, scrollY);
        tileGridDrawer.draw(graphics, 20, 20);

//        Rect visibleMapExtents = getVisibleMapExtents();
//        graphics.drawRect((float) visibleMapExtents.x, (float) visibleMapExtents.y,400,400);
    }

    public Rect getVisibleMapExtents() {
        Rect rect = new Rect();

        double[] scrollPosition = tileGridDrawer.getScrollPosition();
        rect.x = scrollPosition[0];
        rect.y = scrollPosition[1];

        int[] windowSizeInTiles = tileGridDrawer.getWindowSizeInTiles();
        rect.w = (windowSizeInTiles[0] * 16);
        rect.h = (windowSizeInTiles[1] * 16);


        return rect;
    }
}
