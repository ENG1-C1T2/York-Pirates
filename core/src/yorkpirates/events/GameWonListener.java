package yorkpirates.events;


import yorkpirates.GameScreen;

public class GameWonListener implements EventListener {
    GameScreen game;

    public GameWonListener(GameScreen game) { this.game = game; }

    @Override
    public void on(Event event) {
        if (event instanceof GameWon) {
            game.pause();
        }
    }
}