package yorkpirates;

import yorkpirates.events.Event;
import yorkpirates.events.EventListener;
import yorkpirates.events.SurvivedFiveSeconds;

public class GameStats implements EventListener {
    int points;
    int plunder;

    public GameStats() {
        points = 0;
        plunder = 0;
    }

    public int getPoints() { return points; }

    public int getPlunder() { return plunder; }


    @Override
    public void on(Event event) {
        if (event instanceof SurvivedFiveSeconds) { points += 1; }
    }
}