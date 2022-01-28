package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import yorkpirates.events.EventDispatcher;
import yorkpirates.objects.AIShip;
import yorkpirates.objects.GameObject;
import yorkpirates.objects.PlayerShip;
import yorkpirates.ui.MovementHint;

/**
 * The main gameplay screen where the player controls their ship.
 */
public class GameScreen implements Screen {
    public final EventDispatcher events;
    public final PlayerShip player;
    public final Camera camera;

    private final Batches batches;

    private final Array<GameObject> gameObjects;

    private final Texture background;

    public GameScreen() {
        events = new EventDispatcher();
        player = new PlayerShip();
        camera = new Camera();

        background = new Texture(Gdx.files.internal("ocean.jpg"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        batches = new Batches();

        gameObjects = new Array<>(false, 16, GameObject.class);

        addObject(player);
        addObject(new AIShip());
        addObject(new AIShip());
        addObject(new MovementHint());
    }

    @Override
    public void render(float delta) {
        camera.trackShip(player);

        // Fill the screen with the ocean image.
        batches.screen.begin();
        batches.screen.draw(background,
                0, 0,
                (int) camera.position.x, (int) -camera.position.y,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batches.screen.end();

        for (GameObject gameObject : gameObjects) {
            gameObject.update(this, delta);
        }

        batches.world.setProjectionMatrix(camera.combined);

        for (GameObject gameObject : gameObjects) {
            gameObject.render(batches);
        }
    }

    public void addObject(GameObject object) {
        final int depth = object.getDepth();

        int i;
        for (i = 0; i < gameObjects.size; i++) {
            if (gameObjects.get(i).getDepth() <= depth) {
                break;
            }
        }
        gameObjects.insert(i, object);

        object.create(this);
    }

    public void removeObject(GameObject object) {
        gameObjects.removeValue(object, true);
        object.dispose();
    }

    @Override
    public void show() {

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
        for (GameObject gameObject : gameObjects) {
            gameObject.dispose();
        }

        batches.dispose();
    }

    public static class Batches {
        public final SpriteBatch world;
        public final SpriteBatch screen;

        private Batches() {
            world = new SpriteBatch();
            screen = new SpriteBatch();
        }

        private void dispose() {
            world.dispose();
            screen.dispose();
        }
    }
}
