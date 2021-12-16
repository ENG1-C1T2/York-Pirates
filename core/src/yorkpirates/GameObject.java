package yorkpirates;

/**
 * Any entity in the game, be it a Ship, UI element or whatever.
 */
public interface GameObject {
    void create(final YorkPirates game);
    void render();
    void dispose();
}
