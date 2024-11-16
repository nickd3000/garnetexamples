package com.physmo.garnetexamples.games.dogmatrix.components;

import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnet.toolkit.scene.SceneManager;
import com.physmo.garnet.toolkit.simplecollision.ColliderComponent;
import com.physmo.garnetexamples.games.dogmatrix.PickupType;
import com.physmo.garnetexamples.games.dogmatrix.Resources;

import java.util.HashMap;
import java.util.Map;

public class Pickup extends Component {

    private final PickupType pickupType;
    Resources resources;
    Map<PickupType, int[]> pickupMap;

    public Pickup(PickupType pickupType) {
        this.pickupType = pickupType;

    }

    public PickupType getPickupType() {
        return pickupType;
    }

    @Override
    public void init() {
        resources = SceneManager.getSharedContext().getObjectByType(Resources.class);

        ColliderComponent colliderComponent = parent.getComponent(ColliderComponent.class);

        colliderComponent.setCallbackEnter(target -> {
            if (target.hasTag("player")) {
                parent.destroy();
            }
        });

        pickupMap = new HashMap<>();
        pickupMap.put(PickupType.backwards_gun, new int[]{0, 3});
        pickupMap.put(PickupType.four_way_gun, new int[]{1, 3});
        pickupMap.put(PickupType.fire_rate_up, new int[]{2, 3});
        pickupMap.put(PickupType.speed_up, new int[]{3, 3});
        pickupMap.put(PickupType.bone, new int[]{1, 1});

    }

    @Override
    public void tick(double t) {

    }

    @Override
    public void draw(Graphics g) {
        g.setActiveViewport(1);

        int[] sprImage = pickupMap.get(pickupType);
        g.drawImage(resources.spriteTileSheet, parent.getTransform().x, parent.getTransform().y, sprImage[0], sprImage[1]);

    }
}
