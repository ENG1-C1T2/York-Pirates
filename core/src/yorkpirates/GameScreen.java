package yorkpirates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import yorkpirates.events.EventDispatcher;
import yorkpirates.objects.WinTrigger;
import yorkpirates.objects.*;
import yorkpirates.ui.EventReactions;
import yorkpirates.ui.MovementHint;
import yorkpirates.quests.QuestManager;
import yorkpirates.ui.QuestPrintout;
import yorkpirates.ui.StatusPrintout;

/**
 * The main gameplay screen where the player controls their ship.
 */
public class GameScreen implements Screen {
    public final EventDispatcher events;
    public final PlayerShip player;
    public final Camera camera;
    public final WaterSim waterSim;
    public final Batches batches;
    public final College[] colleges;
    public final GameStats stats;

    private final Array<GameObject> gameObjects;
    private Boolean paused;

    /**
     * Creates a new GameScreen, effectively a running instance of the game.
     */
    public GameScreen() {
        events = new EventDispatcher(this);
        player = new PlayerShip();
        camera = new Camera();
        waterSim = new WaterSim();
        stats = new GameStats(events);

        batches = new Batches();

        paused = false;

        gameObjects = new Array<>(true, 16, GameObject.class);

        int rad = waterSim.getMinRadius();
        colleges = new College[]{new College("Your College", -60, 150, 0),
                new College("Goodricke", -0.48*rad, 0.5*rad, 2),
                new College("Derwent", 0.6*rad, 0.28*rad, 2),
                new College("Constantine", -0.32*rad, -0.48*rad, 2),
                new College("Vanbrugh", 0.44*rad, -0.56*rad, 2)};

        addObject(waterSim);
        for (College college : colleges) {
            addObject(college);
        }

        addObject(player);
        addObject(new AIShip());
        addObject(new AIShip());
        addObject(new MovementHint());
        addObject(new FabricFilter());

        QuestManager questManager = new QuestManager();
        addObject(questManager);
        addObject(new QuestPrintout(this, questManager));

        addObject(new StatusPrintout(this));
        addObject(new EventReactions());
        addObject(new SurvivalTimer());
        addObject(new WinTrigger());

        addObject(new DevPowers());
    }

    /**
     * Update and render all GameObjects in the GameScreen.
     */
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

    /**
     * Render all GameObjects in the GameScreen.
     */
    private void renderObjects() {
        // Insertion-sort objects with the same depth,
        // based on their y value if they have one.
        // This allows behaviour such as UI elements always staying on top
        // of other objects, while also enabling more dynamic, 3d-like interactions
        // such as ships rendering in order of their 'proximity' to the camera,
        // i.e. which has the lower y value.

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

    /**
     * Create a new GameObject.
     * @param object The object to be created.
     */
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

    /**
     * Remove a GameObject
     * @param object The object to be removed.
     */
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

    /**
     * SpriteBatches to be used for rendering objects in the game.
     */
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
