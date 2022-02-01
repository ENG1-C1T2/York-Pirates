package yorkpirates.quests;

import yorkpirates.events.Event;
import yorkpirates.events.EventDispatcher;

import java.util.Random;

/**
 * A quest for the player to kill a certain number of enemies.
 */
public class KillXEnemies implements Quest {
    private final int requirement;
    private int progress;

    /**
     * Creates a new KillXEnemies quest where X is a random
     * integer between 1 and 5 inclusive.
     *
     * @param events An EventDispatcher for this quest to
     *               register with.
     */
    public KillXEnemies(EventDispatcher events) {
        requirement = new Random().nextInt(5) + 1;
        progress = 0;

        events.register("KilledEnemy", (dispatcher, event) -> onKilledEnemy(event));
    }

    private void onKilledEnemy(Event event) {
        progress++;
    }

    @Override
    public boolean isCompleted() {
        return progress >= requirement;
    }

    @Override
    public String showProgress() {
        // E.g. 'Killed 3/5 enemies'
        return String.format("Killed %d/%d enemies", progress, requirement);
    }
}
