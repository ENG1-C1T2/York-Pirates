package yorkpirates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Any entity in the game, be it a Ship, UI element or whatever.
 */
public interface GameObject {
    void create(final YorkPirates game);
    void update(final YorkPirates game);
    void render(SpriteBatch batch);
    void dispose();
//    int depth();
}
