package yorkpirates.objects;

import yorkpirates.GameScreen;

/**
 * Any entity in the game, be it a Ship, UI element or whatever.
 */
public interface GameObject {
    void create(final GameScreen game);
    void update(final GameScreen game);
    void render(final GameScreen.Batches batches);
    void dispose();
//    int depth();
}
