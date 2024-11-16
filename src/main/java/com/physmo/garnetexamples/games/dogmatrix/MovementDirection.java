package com.physmo.garnetexamples.games.dogmatrix;

public enum MovementDirection {
    Up(0, -1),
    Down(0, 1),
    Left(-1, 0),
    Right(1, 0),
    Stopped(0, 0);

    final int hDir;
    final int vDir;

    MovementDirection(int hDir, int vDir) {
        this.hDir = hDir;
        this.vDir = vDir;
    }

    public int getHDir() {
        return hDir;
    }

    public int getVDir() {
        return vDir;
    }

    public boolean isOpposite(MovementDirection movementDirection) {
        if (this == Up && movementDirection == Down) return true;
        if (this == Down && movementDirection == Up) return true;
        if (this == Left && movementDirection == Right) return true;
        return this == Right && movementDirection == Left;
    }
}
