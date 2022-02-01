package yorkpirates;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * The logic shared across the entire application.
 *
 * Should delegate to various Screens for most functionality,
 * e.g. GameScreen for the main gameplay, some menu screen for
 * the main menu, etc.
 */
public class YorkPirates extends Game {
	private GameScreen gameScreen;

	/**
	 * Create a new GameScreen.
	 */
	@Override
	public void create() {
		gameScreen = new GameScreen();
		this.setScreen(gameScreen);
	}

	/**
	 * Call Game render function and exit the game when the escape key is pressed.
	 */
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
		super.dispose();
		gameScreen.dispose();
	}
}
