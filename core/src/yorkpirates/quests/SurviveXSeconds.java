package yorkpirates.quests;

import com.badlogic.gdx.utils.TimeUtils;
import yorkpirates.events.EventDispatcher;

import java.util.Random;

/**
 * A quest for the player to survive a certain number of seconds.
 */
public class SurviveXSeconds implements Quest {
    private final int requirement;
    private final long startTime;

    /**
     * Creates a new SurviveXSeconds quest where X is a random
     * integer between 15 and 60 inclusive.
     *
     * @param events An EventDispatcher for this quest to
     *               register with.
     */
    public SurviveXSeconds(EventDispatcher events) {
        requirement = new Random().nextInt(46) + 15;
        startTime = TimeUtils.millis();
    }

    @Override
    public boolean isCompleted() {
        return secondsSoFar() >= requirement;
    }

    @Override
    public String showProgress() {
        return String.format("Survive %d seconds", requirement - secondsSoFar());
    }

    private int secondsSoFar() {
        return (int) Math.floor(TimeUtils.timeSinceMillis(startTime) / 1000d);
    }
}
