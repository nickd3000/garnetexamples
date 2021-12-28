package com.physmo.garnettest.invaders.components;

import com.physmo.garnet.collision.BoxCollider2D;
import com.physmo.garnet.collision.CollisionPacket;
import com.physmo.garnet.entity.Component;
import com.physmo.garnet.entity.Entity;
import com.physmo.garnettest.invaders.GameData;

import java.util.List;

public class ComponentEnemy extends Component {

    GameData gameData;
    double fireDelay;

    @Override
    public void init() {
        BoxCollider2D boxCollider2D = new BoxCollider2D();
        boxCollider2D.setValues(parent, -7, -7, 14, 14);
        parent.addCollider(boxCollider2D);
        gameData = (GameData) parent.garnet.getGlobalObject("game_data");
        resetFireDelay();
    }

    public void resetFireDelay() {
        fireDelay = 2+Math.random()*4;
    }


    @Override
    public void tick(double delta) {

        ComponentGameLogic gameLogic = getComponent(ComponentGameLogic.class);

        if (gameLogic != null) {
            if (gameLogic.dir) {
                parent.position.x += 10 * delta;
            } else {
                parent.position.x -= 10 * delta;
            }
        }

        fireDelay-=delta;
        if (fireDelay<0) {
            fireMissile();
            resetFireDelay();
        }
    }

    @Override
    public void onCollisionStart(CollisionPacket collisionPacket) {
        if (!collisionPacket.sourceEntity.hasTag("player_missile")) return;
        System.out.println("enemy collision");
        parent.setActive(false);
        gameData.currentScore++;
        System.out.println("current score: "+gameData.currentScore);
    }

    private void fireMissile() {

        List<Entity> missiles = parent.getGameState().getEntitiesByTag("enemy_missile");
        for (Entity missile : missiles) {
            if (!missile.getActive()) {
                missile.setActive(true);
                missile.setVisible(true);
                missile.position.x = parent.position.x;
                missile.position.y = parent.position.y;

                break;
            }
        }

    }
}
