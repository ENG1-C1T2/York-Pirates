package yorkpirates.objects;

import yorkpirates.GameScreen;

/**
 * Any entity in the game, be it a Ship, UI element or whatever.
 */
public interface GameObject {
    void create(final GameScreen game);
    void update(final GameScreen game, final float delta);
    void render(final GameScreen game);
    void dispose();
    int getDepth();
}
