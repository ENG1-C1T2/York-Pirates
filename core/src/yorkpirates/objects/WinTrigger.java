package yorkpirates.objects;

import yorkpirates.GameScreen;
import yorkpirates.events.Event;
import yorkpirates.events.GameWon;
import yorkpirates.ui.EndGamePrintout;

/**
 * Listens for the Event that signals the end of the game,
 * and prompts the user to restart the game.
 */
public class WinTrigger implements GameObject {
    public void onGameWon(GameScreen game, Event event) {
        game.events.trigger(new GameWon());
        game.addObject(new EndGamePrintout(game.stats.getPoints()));
    }

    @Override
    public void create(GameScreen game) {
        game.events.register("CompletedAllQuests", this::onGameWon);
    }

    @Override
    public void update(GameScreen game, float delta) {

    }

    @Override
    public void render(GameScreen game) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getDepth() {
        return 0;
    }
}
