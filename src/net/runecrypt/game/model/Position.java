package net.runecrypt.game.model;

/**
 * An class that is used to represent the position of an {@link Entity} on the
 * rs2 map.
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 */
public final class Position {

    /**
     * The x, y, and height.
     */
    private int x, y, height;

    /**
     * Creates an new {@link Position}.
     *
     * @param x      The position x.
     * @param y      The position y.
     * @param height The height.
     * @return The new position.
     */
    public static Position create(int x, int y, int height) {
        return new Position(x, y, height);
    }

    /**
     * Constructs a new Position instance.
     *
     * @param x      The position x.
     * @param y      The position y.
     * @param height The height or plane.
     */
    public Position(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    /**
     * Gets the x position.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the local x position.
     *
     * @return the local x position.
     */
    public int getLocalX() {
        return getX() - 8 * (getRegionX() - 6);
    }

    /**
     * Gets the local x position.
     *
     * @param pos The position we're getting it from.
     * @return The local x position.
     */
    public int getLocalX(Position pos) {
        return pos.getX() - 8 * (pos.getRegionX() - 6);
    }

    /**
     * Gets the region coord for the x position.
     *
     * @return The region.
     */
    public int getRegionX() {
        return (getX() >> 3);
    }

    /**
     * Gets the y position.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the local position y.
     *
     * @return the position y.
     */
    public int getLocalY() {
        return getY() - 8 * (getRegionY() - 6);
    }

    /**
     * Gets the local y position.
     *
     * @param pos The position we're getting it from.
     * @return The local y position.
     */
    public int getLocalY(Position pos) {
        return pos.getY() - 8 * (pos.getRegionY() - 6);
    }

    /**
     * Gets the region y position coord.
     *
     * @return the y position.
     */
    public int getRegionY() {
        return (getY() >> 3);
    }

    /**
     * Gets the height position.
     *
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Checks if an {@link Entity}'s position is within distance of another
     * entity.
     *
     * @param position The position of the entity.
     * @param radius   The radius to check for.
     * @return Wether or not the entity is within distance.
     */
    public boolean withinDistance(Position position, int radius) {
        if (position.getHeight() != getHeight())
            return false;

        int deltaX = position.getX() - getX(), deltaY = position.getY() - getY();
        return (deltaX <= (radius) && deltaX >= (0 - radius - 1) && deltaY <= (radius) && deltaY >= (0 - radius - 1));
    }

    /**
     * Checks if an {@link Entity}'s position is within distance of another
     * entity.
     *
     * @param position The position of the entity.
     * @return Wether or not the entity is within distance.
     */
    public boolean withinDistance(Position position) {
        if (position.getHeight() != getHeight())
            return false;

        int deltaX = position.getX() - getX(), deltaY = position.getY() - getY();
        return (deltaX <= (14) && deltaX >= (-15) && deltaY <= (14) && deltaY >= (-15));
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return getHeight() << 30 | getX() << 15 | getY();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object other) {
        if (!(other instanceof Position))
            return false;

        Position pos = (Position) other;
        return pos.getX() == getX() && pos.getY() == getY() && pos.getHeight() == getHeight();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "Position [" + getX() + "," + getY() + "," + getHeight() + "]";
    }

    public int get18BitsHash() {
        int regionId = ((getRegionX() / 8) << 8) + (getRegionY() / 8);
        return (((regionId & 0xff) << 6) >> 6) | (getHeight() << 16) | ((((regionId >> 8) << 6) >> 6) << 8);
    }

    public int get30BitsHash() {
        return y | height << 28 | x << 14;
    }
}
