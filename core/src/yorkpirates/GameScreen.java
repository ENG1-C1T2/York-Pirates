package yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import yorkpirates.events.EventDispatcher;
import yorkpirates.events.SurvivedFiveSeconds;
import yorkpirates.objects.AIShip;
import yorkpirates.objects.GameObject;
import yorkpirates.objects.PlayerShip;
import yorkpirates.ui.MovementHint;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * The main gameplay screen where the player controls their ship.
 */
public class GameScreen implements Screen {
    public final EventDispatcher events;
    public final PlayerShip player;
    public final Camera camera;
    public GameStats stats;

    private final Batches batches;

    private final SurvivedFiveSeconds SurvivedFiveSeconds;

    private final Array<GameObject> gameObjects;
    private final Array<GameObject> hudObjects;

    private final Texture background;

    private long pointTimer;

    public GameScreen() {
        events = new EventDispatcher();
        player = new PlayerShip();
        camera = new Camera();
        stats = new GameStats();

        SurvivedFiveSeconds = new SurvivedFiveSeconds();

        events.register(SurvivedFiveSeconds, stats);



        pointTimer = TimeUtils.nanoTime();

        background = new Texture(Gdx.files.internal("ocean.jpg"));
        background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        batches = new Batches();

        gameObjects = new Array<>(false, 16, GameObject.class);
        hudObjects = new Array<>(false, 16, GameObject.class);

        addObject(player);
        addObject(new AIShip());
        addHudObject(new MovementHint());
    }

    @Override
    public void render(float delta) {
        if ((TimeUtils.nanoTime() - pointTimer)/1000000000 > 5) {
            events.trigger(SurvivedFiveSeconds);
            pointTimer = TimeUtils.nanoTime();
        }


        camera.trackShip(player);

        // Fill the screen with the ocean image.
        batches.screen.begin();
        batches.screen.draw(background,
                0, 0,
                (int) camera.position.x, (int) -camera.position.y,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batches.screen.end();

        for (GameObject gameObject : gameObjects) {
            gameObject.update(this);
        }

        batches.world.setProjectionMatrix(camera.combined);
        batches.world.begin();

        for (GameObject gameObject : gameObjects) {
            gameObject.render(batches);
        }

        batches.world.end();

        batches.screen.begin();

        for (GameObject hudObject : hudObjects) {
            hudObject.update(this);
            hudObject.render(batches);
        }

        batches.screen.end();
    }

    public void addObject(GameObject object) {
        gameObjects.add(object);
        object.create(this);
    }

    public void addHudObject(GameObject object) {
        hudObjects.add(object);
        object.create(this);
    }

    public void removeObject(GameObject object) {
        gameObjects.removeValue(object, true);
        object.dispose();
    }

    public void removeHudObject(GameObject object) {
        hudObjects.removeValue(object, true);
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

        for (GameObject hudObject: hudObjects) {
            hudObject.dispose();
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
