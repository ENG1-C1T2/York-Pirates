package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

/**
 * The main gameplay screen where the player controls their ship.
 */
public class GameScreen implements Screen {
    private final YorkPirates game;

    private final Texture background;

    public GameScreen(final YorkPirates game) {
        this.game = game;

        background = new Texture(Gdx.files.internal("ocean.jpg"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.camera.position.x = game.player.ship.x + game.player.ship.width/2;
        game.camera.position.y = game.player.ship.y + game.player.ship.height/2;
        game.camera.update();

        // Fill the screen with the ocean image.
        game.hudBatch.begin();
        game.hudBatch.draw(background,
                0, 0,
                (int) game.camera.position.x, (int) -game.camera.position.y,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.hudBatch.end();

        for (GameObject gameObject : game.gameObjects) {
            gameObject.update(game);
        }

        game.batch.setProjectionMatrix(game.camera.combined);
        game.batch.begin();

        for (GameObject gameObject : game.gameObjects) {
            gameObject.render(game.batch);
        }

        game.batch.end();

        game.hudBatch.begin();

        for (GameObject hudObject : game.hudObjects) {
            hudObject.update(game);
            hudObject.render(game.hudBatch);
        }

        game.hudBatch.end();
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
