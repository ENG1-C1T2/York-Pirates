package yorkpirates.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;

/**
 * Prompts the user to restart when the game is over.
 */
public class EndGamePrintout extends Printout {
    public EndGamePrintout(final int points) {
        super(
                -Gdx.graphics.getWidth()/2f,
                Gdx.graphics.getHeight()/3f,
                Gdx.graphics.getWidth(),
                Align.center,
                () -> String.format("Final score: %d\nPress 'r' to restart.", points)
        );
    }
}
