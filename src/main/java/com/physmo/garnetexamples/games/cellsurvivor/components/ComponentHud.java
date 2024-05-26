package com.physmo.garnetexamples.games.cellsurvivor.components;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.text.RegularFont;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnettoolkit.Component;
import com.physmo.garnettoolkit.color.ColorUtils;
import com.physmo.garnettoolkit.scene.SceneManager;

public class ComponentHud extends Component {
    Resources resources;
    Graphics g;
    Garnet garnet;

    int trackedScore = 0;

    @Override
    public void init() {
        resources = parent.getContext().getObjectByType(Resources.class);
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        g = garnet.getGraphics();
    }

    @Override
    public void tick(double t) {
        int actualScore = resources.getCurrentScore();
        if (trackedScore < actualScore) trackedScore += 1;
    }

    @Override
    public void draw() {
        g.setActiveCamera(Constants.scorePanelCameraId);
        RegularFont regularFont = resources.getRegularFont();
        regularFont.setScale(1);
        g.setColor(ColorUtils.YELLOW);
        regularFont.drawText(g, "0" + trackedScore, 3, 3);
    }
}
