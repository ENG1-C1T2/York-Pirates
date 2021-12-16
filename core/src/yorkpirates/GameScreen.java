package yorkpirates;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * The main gameplay screen where the player controls their ship.
 */
public class GameScreen implements Screen {
    private final YorkPirates game;

    public GameScreen(final YorkPirates game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Fill the screen with a blue colour.
        ScreenUtils.clear(0, 0.6f, 1, 1);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
