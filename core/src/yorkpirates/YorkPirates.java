package yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

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

	Array<GameObject> gameObjects;

	private GameScreen gameScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameObjects = new Array<>(false, 16, GameObject.class);

		addObject(new PlayerShip());

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

	public void removeObject(GameObject object) {
		gameObjects.removeValue(object, true);
		object.dispose();
	}
}
