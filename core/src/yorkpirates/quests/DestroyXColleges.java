package yorkpirates.quests;

import yorkpirates.events.Event;
import yorkpirates.events.EventDispatcher;

import java.util.Random;

/**
 * A quest for the player to destroy a certain number of colleges.
 */
public class DestroyXColleges implements Quest {
    private final int requirement;
    private int progress;

    /**
     * Creates a new DestroyXColleges quest where X is a random
     * integer between 1 and 3 inclusive.
     *
     * @param events An EventDispatcher for this quest to
     *               register with.
     */
    public DestroyXColleges(EventDispatcher events) {
        requirement = new Random().nextInt(3) + 1;
        progress = 0;

        events.register("DestroyedCollege", (dispatcher, event) -> onDestroyedCollege(event));
    }

    private void onDestroyedCollege(Event event) {
        progress++;
    }

    @Override
    public boolean isCompleted() {
        return progress >= requirement;
    }

    @Override
    public String showProgress() {
        // E.g. 'Destroyed 1/3 colleges'
        return String.format("Destroyed %d/%d colleges", progress, requirement);
    }
}
