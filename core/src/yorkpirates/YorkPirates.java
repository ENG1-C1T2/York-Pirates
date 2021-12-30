package yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

	PlayerController playerController;
	Ship playerShip;

	private GameScreen gameScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		playerController = new PlayerController();
		playerShip = new Ship(playerController);
		playerShip.create(this);

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
		batch.dispose();
		gameScreen.dispose();
		playerShip.dispose();
	}
}
