package yorkpirates.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Align;
import yorkpirates.GameScreen;
import yorkpirates.quests.QuestManager;

/**
 * Prints the status of a QuestManager to the screen.
 */
public class QuestPrintout extends Printout {
    /**
     * Creates a new QuestPrintout tied to the given QuestManager.
     *
     * @param game the active GameScreen.
     * @param questManager the QuestManager about which to report.
     */
    public QuestPrintout(GameScreen game, QuestManager questManager) {
        super(
                Gdx.graphics.getWidth() / 2f - Gdx.graphics.getWidth() / 3f - 25,
                Gdx.graphics.getHeight() / 2f - 25,
                Gdx.graphics.getWidth() / 3f,
                Align.right,
                questManager.createSupplier(game)
        );
    }
}
