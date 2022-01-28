package yorkpirates;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import yorkpirates.events.EventDispatcher;
import yorkpirates.objects.*;
import yorkpirates.ui.MovementHint;

/**
 * The main gameplay screen where the player controls their ship.
 */
public class GameScreen implements Screen {
    public final EventDispatcher events;
    public final PlayerShip player;
    public final Camera camera;
    public final WaterSim waterSim;
    public final Batches batches;

    private final Array<GameObject> gameObjects;

    public GameScreen() {
        events = new EventDispatcher();
        player = new PlayerShip();
        camera = new Camera();
        waterSim = new WaterSim();

        batches = new Batches();

        gameObjects = new Array<>(false, 16, GameObject.class);

        addObject(waterSim);
        addObject(player);
        addObject(new AIShip());
        addObject(new AIShip());
        addObject(new MovementHint());
    }

    @Override
    public void render(float delta) {
        camera.trackShip(player);

        for (GameObject gameObject : gameObjects) {
            gameObject.update(this, delta);
        }

        batches.world.setProjectionMatrix(camera.combined);

        for (GameObject gameObject : gameObjects) {
            gameObject.render(this);
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
