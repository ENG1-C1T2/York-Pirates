package yorkpirates.objects;

import yorkpirates.GameScreen;

/**
 * Any entity in the game, be it a Ship, UI element or whatever.
 */
public interface GameObject {
    /**
     * Called when the GameObject is added to the game world.
     * @param game The active GameScreen.
     */
    void create(final GameScreen game);

    /**
     * Called once per frame, should be used to update the object's logic.
     * @param game The active GameScreen.
     * @param delta The amount of time since the last update.
     */
    void update(final GameScreen game, final float delta);

    /**
     * Called to trigger the GameObject to render itself to the screen.
     * @param game The active GameScreen.
     */
    void render(final GameScreen game);

    /**
     * Called when the GameObject is removed from the game, or the game ends.
     */
    void dispose();

    /**
     * A getter function for the object's depth i.e. how high in the rendering order
     * it should be.
     * @return An integer where a higher number means it will be drawn earlier.
     */
    int getDepth();
}
