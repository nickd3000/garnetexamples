package com.physmo.garnetexamples.games.cellsurvivor.scenes;

import com.physmo.garnet.Garnet;
import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.input.InputAction;
import com.physmo.garnet.input.InputKeys;
import com.physmo.garnet.text.RegularFont;
import com.physmo.garnet.toolkit.scene.Scene;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnetexamples.games.cellsurvivor.Constants;
import com.physmo.garnetexamples.games.cellsurvivor.Resources;
import com.physmo.garnetexamples.games.cellsurvivor.components.ComponentPlayerCapabilities;
import com.physmo.garnetexamples.games.cellsurvivor.components.PlayerCapability;

import java.util.ArrayList;
import java.util.List;

public class SceneLevelUp extends Scene {

    Garnet garnet;
    Resources resources;
    RegularFont regularFont;
    ComponentPlayerCapabilities playerCapabilities;
    List<PlayerCapability> threeCapabilities = new ArrayList<>();
    int selectedCapability = 0;

    public SceneLevelUp(String name) {
        super(name);
    }

    @Override
    public void init() {
        garnet = SceneManager.getSharedContext().getObjectByType(Garnet.class);
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        regularFont = resources.getRegularFont();
    }

    @Override
    public void tick(double delta) {

        if (garnet.getInput().getKeyboard().isKeyFirstPress(InputKeys.KEY_L)) {
            SceneManager.popSubScene("levelUp");
        }

        if (garnet.getInput().isActionKeyFirstPress(InputAction.UP)) {
            selectedCapability--;
            if (selectedCapability < 0) {
                selectedCapability = 2;
            }
        }
        if (garnet.getInput().isActionKeyFirstPress(InputAction.DOWN)) {
            selectedCapability++;
            if (selectedCapability > 2) {
                selectedCapability = 0;
            }
        }

        boolean confirm = garnet.getInput().isActionKeyFirstPress(InputAction.FIRE1);
        if (garnet.getInput().getKeyboard().isKeyFirstPress(InputKeys.KEY_ENTER)) confirm = true;
        if (confirm) {
            PlayerCapability pc = threeCapabilities.get(selectedCapability);
            playerCapabilities.increaseLevel(pc);
            SceneManager.popSubScene("levelUp");
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setDrawOrder(Constants.DRAW_ORDER_PAUSE_BACKGROUND);
        g.setActiveViewport(Constants.overlayViewportId);
        int[] bufferSize = garnet.getDisplay().getBufferSize();
        g.filledRect(10, 10, 200 * 2, 150);
        drawPlayerDetails(g);
    }

    @Override
    public void onMakeActive() {
        threeCapabilities = getThreeCapabilities();
    }

    @Override
    public void onMakeInactive() {

    }

    public void drawPlayerDetails(Graphics g) {
        g.setColor(0x005500ff);
        //g.setDrawOrder(Constants.DRAW_ORDER_PAUSE_BACKGROUND+1);
        regularFont.setScale(2);
        regularFont.drawText(g, "LEVEL UP", 100, 100);

        regularFont.setScale(1);

        int lineNumber = 0;
        for (PlayerCapability pc : threeCapabilities) {
            if (selectedCapability == lineNumber) {
                g.setColor(0xFF0000ff);
            } else {
                g.setColor(0x000000ff);
            }
            drawCapability(g, pc, pc.getName(), 30, 50 + lineNumber * 15);
            lineNumber++;
        }

    }

    public List<PlayerCapability> getThreeCapabilities() {
        List<PlayerCapability> selected = new ArrayList<>();
        while (selected.size() < 3) {
            PlayerCapability pc = getRandomCapability();
            if (!selected.contains(pc)) {
                selected.add(pc);
            }
        }
        return selected;
    }

    public PlayerCapability getRandomCapability() {
        return PlayerCapability.values()[(int) (Math.random() * PlayerCapability.values().length)];
    }

    public void drawCapability(Graphics g, PlayerCapability id, String description, int x, int y) {
        int val = playerCapabilities.getLevel(id);
        regularFont.drawText(g, description + "  " + val + " -> " + (val + 1), x, y);
    }

    public void setPlayerCapabilities(ComponentPlayerCapabilities playerCapabilities) {

        this.playerCapabilities = playerCapabilities;

    }


}
