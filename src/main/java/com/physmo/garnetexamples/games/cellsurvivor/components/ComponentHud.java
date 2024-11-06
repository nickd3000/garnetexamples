package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.ColorUtils;
import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.text.RegularFont;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;

public class ComponentHud extends Component {
    Resources resources;
    Graphics g;
    Garnet garnet;
    ComponentPlayer player;
    ComponentGameLogic gameLogic;

    int trackedScore = 0;

    @Override
    public void init() {
        resources = parent.getContext().getObjectByType(Resources.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        g = garnet.getGraphics();
        player = parent.getContext().getComponent(ComponentPlayer.class);
        gameLogic = parent.getContext().getComponent(ComponentGameLogic.class);
    }

    @Override
    public void tick(double t) {
        int actualScore = gameLogic.getCurrentScore();
        if (trackedScore < actualScore) trackedScore += 1;
    }

    @Override
    public void draw(Graphics g) {
        g.setActiveViewport(Constants.scorePanelCameraId);
        RegularFont regularFont = resources.getRegularFont();
        regularFont.setScale(1);
        g.setColor(ColorUtils.YELLOW);
        regularFont.drawText(g, "0" + trackedScore, 3, 3);

        int playerLevel = gameLogic.getPlayerLevel();
        regularFont.drawText(g, "Level " + playerLevel, 3 + 100, 3);

        int xp = gameLogic.xp;
        int targetxp = gameLogic.getXpToLevelUp();
        regularFont.drawText(g, "xp " + xp + "/" + targetxp, 3 + 200, 3);
    }
}
