package yorkpirates.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import yorkpirates.GameScreen;

/**
 * A Printout that displays general information about the
 * state of the game, such as the player's health, points, and plunder.
 */
public class StatusPrintout extends Printout {
    /**
     * Creates a new StatusPrintout.
     *
     * @param game The active GameScreen.
     */
    public StatusPrintout(GameScreen game) {
        super(
                25 - Gdx.graphics.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f - 25,
                Gdx.graphics.getWidth() / 3f,
                Align.left,
                () -> String.format("Health: %d\nPoints: %d\nPlunder: %d",
                        game.player.getHealth(), game.stats.getPoints(), game.stats.getPlunder()
                )
        );
    }
}
