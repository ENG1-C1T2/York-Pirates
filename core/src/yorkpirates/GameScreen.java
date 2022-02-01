package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import yorkpirates.events.EventDispatcher;
import yorkpirates.events.GameWon;
import yorkpirates.events.GameWonListener;
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
    public final GameWonListener gameWonListener;
    public final GameWon gameWon;

    private final Array<GameObject> gameObjects;
    private Boolean paused;

    public GameScreen() {
        events = new EventDispatcher();
        player = new PlayerShip();
        camera = new Camera();
        waterSim = new WaterSim();

        gameWon = new GameWon();
        gameWonListener = new GameWonListener(this);
        events.register(gameWon, gameWonListener);

        batches = new Batches();

        paused = false;

        gameObjects = new Array<>(true, 16, GameObject.class);

        addObject(waterSim);
        addObject(player);
        addObject(new AIShip());
        addObject(new AIShip());
        addObject(new MovementHint());
        addObject(new FabricFilter());
    }

    @Override
    public void render(float delta) {
        if (paused) {
            if (Gdx.input.isKeyPressed(Input.Keys.R)) {
                paused = false;
            } else {
                delta = 0;
            }
        }


        camera.trackShip(player);

        for (GameObject gameObject : gameObjects) {
            gameObject.update(this, delta);
        }

        batches.world.setProjectionMatrix(camera.combined);

        renderObjects();
    }

    private void renderObjects() {
        // Insertion-sort objects with the same depth,
        // based on their y value if they have one.
        final Array<HasTransform> orderedLayer = new Array<>(gameObjects.size);
        final Array<GameObject> unorderedLayer = new Array<>(gameObjects.size);
        for (int i = 0; i < gameObjects.size; i++) {
            orderedLayer.clear();
            unorderedLayer.clear();

            int j;
            for (j = 0; i + j < gameObjects.size; j++) {
                GameObject current = gameObjects.get(i + j);

                if (current.getDepth() != gameObjects.get(i).getDepth()) {
                    break;
                }

                if (current instanceof HasTransform) {
                    HasTransform currentWithTransform = (HasTransform) current;

                    int k;
                    for (k = 0; k < orderedLayer.size; k++) {
                        if (orderedLayer.get(k).getTransform().y <= currentWithTransform.getTransform().y) {
                            break;
                        }
                    }
                    orderedLayer.insert(k, currentWithTransform);
                } else {
                    unorderedLayer.add(current);
                }
            }

            for (HasTransform gameObject : orderedLayer) {
                ((GameObject) gameObject).render(this);
            }

            for (GameObject gameObject : unorderedLayer) {
                gameObject.render(this);
            }

            i += j - 1;
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
        paused = true;
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
