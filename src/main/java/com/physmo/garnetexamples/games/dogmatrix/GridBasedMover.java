package com.physmo.garnetexamples.games.dogmatrix;


import com.physmo.garnet.graphics.Graphics;
import com.physmo.garnet.structure.Vector3;
import com.physmo.garnet.toolkit.Component;
import com.physmo.garnetexamples.games.dogmatrix.components.LevelLogic;


public class GridBasedMover extends Component {

    LevelLogic levelLogic;
    MovementDirection movementDirection;
    MovementDirection destinationDirection;
    double speed = 16 * 4;

    public GridBasedMover(LevelLogic levelLogic) {
        this.levelLogic = levelLogic;
        movementDirection = MovementDirection.Down;
        destinationDirection = MovementDirection.Right;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public MovementDirection getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(MovementDirection movementDirection) {
        this.movementDirection = movementDirection;
    }

    public MovementDirection getDestinationDirection() {
        return destinationDirection;
    }

    public void setDestinationDirection(MovementDirection destinationDirection) {
        this.destinationDirection = destinationDirection;
    }

    @Override
    public void init() {

    }

    @Override
    public void tick(double t) {

        Vector3 nextPosition = new Vector3(parent.getTransform());
        nextPosition.x += movementDirection.getHDir() * speed * t;
        nextPosition.y += movementDirection.getVDir() * speed * t;

        int tileX = (int) ((parent.getTransform().x + 8) / 16);
        int tileY = (int) ((parent.getTransform().y + 8) / 16);

        // Direction change should occur if the previous position was before the mid-point
        // and the next position would be beyond the mid-point of the tile.
        if (wouldMovementCrossCenter(parent.getTransform(), nextPosition)) {
            boolean destDirectionIsBlocked =
                    levelLogic.isSolid(tileX + destinationDirection.getHDir(),
                            tileY + destinationDirection.getVDir());

            if (movementDirection != destinationDirection && !destDirectionIsBlocked) {
                System.out.println("yes");
                movementDirection = destinationDirection;
            }

            // wall Collision
            boolean movementDirectionIsBlocked =
                    levelLogic.isSolid(tileX + movementDirection.getHDir(),
                            tileY + movementDirection.getVDir());
            if (movementDirectionIsBlocked) movementDirection = MovementDirection.Stopped;

        }

        // If we are stopped, switch to the requested direction if we are not
        // blocked, no need to wait to cross a boundary.
        if (movementDirection == MovementDirection.Stopped) {
            boolean destDirectionIsBlocked =
                    levelLogic.isSolid(tileX + destinationDirection.getHDir(),
                            tileY + destinationDirection.getVDir());
            if (!destDirectionIsBlocked) movementDirection = destinationDirection;
        }

        // Double-back has special handling (no wait for mid-point crossing)
        if (movementDirection.isOpposite(destinationDirection)) {
            movementDirection = destinationDirection;
        }

        parent.getTransform().set(nextPosition);
    }

    @Override
    public void draw(Graphics g) {

    }

    public double[] getOffsetFromTileCenter(double x, double y) {
        double[] offsets = new double[2];
        offsets[0] = ((x + 8) % 16) - 8;
        offsets[1] = ((y + 8) % 16) - 8;
        return offsets;
    }

    public boolean wouldMovementCrossCenter(Vector3 position, Vector3 destination) {
        double[] o1 = getOffsetFromTileCenter(position.x, position.y);
        double[] o2 = getOffsetFromTileCenter(destination.x, destination.y);

        if (Math.abs(o1[0]) > 0.3) return false;
        if (Math.abs(o1[1]) > 0.3) return false;
        if (Math.abs(o2[0]) > 0.3) return false;
        if (Math.abs(o2[1]) > 0.3) return false;

        if (o1[0] >= 0 && o2[0] < 0) return true;
        if (o1[0] <= 0 && o2[0] > 0) return true;
        if (o1[1] >= 0 && o2[1] < 0) return true;
        return o1[1] <= 0 && o2[1] > 0;
    }
}
