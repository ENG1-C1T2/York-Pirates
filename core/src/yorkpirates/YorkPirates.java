package yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import yorkpirates.events.Event;
import yorkpirates.events.EventDispatcher;
import yorkpirates.ui.MovementHint;

/**
 * The logic shared across the entire application.
 *
 * Should delegate to various Screens for most functionality,
 * e.g. GameScreen for the main gameplay, some menu screen for
 * the main menu, etc.
 */
public class YorkPirates extends Game {
	/**
	 * A SpriteBatch for drawing images to the screen. Can be used
	 * by anything with a reference to this Game, i.e. any Screens or
	 * GameObjects that get their code executed.
	 */
	SpriteBatch batch;
	SpriteBatch hudBatch;

	final Array<GameObject> gameObjects;
	final Array<GameObject> hudObjects;

	final EventDispatcher events;
	final PlayerShip player;
	final Camera camera;

	private GameScreen gameScreen;

	public YorkPirates() {
		gameObjects = new Array<>(false, 16, GameObject.class);
		hudObjects = new Array<>(false, 16, GameObject.class);

		events = new EventDispatcher();
		player = new PlayerShip();
		camera = new Camera();
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();

		addObject(player);
		addHudObject(new MovementHint());

		gameScreen = new GameScreen(this);
		this.setScreen(gameScreen);
	}

	@Override
	public void render () {
		super.render();

		// Exit the game when Escape is pressed.
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
	
	@Override
	public void dispose () {
		super.render();
		for (GameObject gameObject : gameObjects) {
			gameObject.dispose();
		}

		batch.dispose();
		gameScreen.dispose();
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
}
