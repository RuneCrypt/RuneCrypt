package net.runecrypt.game.model;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 3:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class Entity {

    private int index;

    /**
     * Gets the index of the current Entity.
     *
     * @return The entity index.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets the index of the current Entity.
     *
     * @param index The index to set.
     */
    public void setIndex(int index) {
        this.index = index;
    }
}
