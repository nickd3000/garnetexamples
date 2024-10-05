package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Camera;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.tilegrid.TileGridData;
import com.physmo.garnet.tilegrid.TileGridDrawer;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.GameObject;
import com.physmo.garnet.toolkit.Rect;
import com.physmo.garnet.toolkit.Vector3;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;

public class ComponentLevelMap extends Component {

    TileGridData tileGridData;
    TileGridDrawer tileGridDrawer;
    int mapWidth = 100;
    int mapHeight = 100;
    Resources resources;
    Graphics graphics;
    Garnet garnet;
    GameObject player;

    int windowWidth = 640 - 20;
    int windowHeight = 480 - 20;

    Camera camera;

    public ComponentLevelMap() {


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
        tileGridDrawer = new TileGridDrawer().setData(tileGridData)
                //.setWindowSize(windowWidth, windowHeight)
                .setTileSize(16, 16).setTileSheet(resources.getSpritesTilesheet())
                //.setScale((int) scale)
                .setCameraId(Constants.tileGridCameraId);

        int grass = resources.getSpritesTilesheet().getTileIndexFromCoords(1, 7);
        int flower = resources.getSpritesTilesheet().getTileIndexFromCoords(2, 7);
        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                if (Math.random() < 0.98) tileGridData.setTileId(x, y, grass);
                else tileGridData.setTileId(x, y, flower);
            }
        }

        camera = garnet.getGraphics().getCameraManager().getCamera(Constants.tileGridCameraId);

        player = parent.getContext().getObjectByTag(Constants.TAG_PLAYER);
    }

    @Override
    public void tick(double t) {
        double zoom = camera.getZoom();
        Vector3 playerPos = player.getTransform();
        double scrollX = camera.getX();
        double scrollY = camera.getY();
        double dx = playerPos.x - (scrollX + ((windowWidth / zoom) / 2));
        double dy = playerPos.y - (scrollY + ((windowHeight / zoom) / 2));
        double speed = 5.0 * t;
        camera.scroll(dx * speed, dy * speed);
//        scrollX += dx * speed;
//        scrollY += dy * speed;

    }

    @Override
    public void draw() {
        //tileGridDrawer.setScale(2);
        //tileGridDrawer.setScroll(scrollX, scrollY);

        tileGridDrawer.draw(graphics, 20, 20);

//        Rect visibleMapExtents = getVisibleMapExtents();
//        graphics.drawRect((float) visibleMapExtents.x, (float) visibleMapExtents.y,400,400);
    }

    public Rect getVisibleMapExtents() {
        double[] r = camera.getVisibleRect();
        Rect rect = new Rect(r[0], r[1], r[2], r[3]);

//        double[] scrollPosition = tileGridDrawer.getScrollPosition();
//        rect.x = scrollPosition[0];
//        rect.y = scrollPosition[1];
//
//        int[] windowSizeInTiles = tileGridDrawer.getWindowSizeInTiles();
//        rect.w = (windowSizeInTiles[0] * 16);
//        rect.h = (windowSizeInTiles[1] * 16);
//

        return rect;
    }
}
