package yorkpirates.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import yorkpirates.GameScreen;
import yorkpirates.events.EarnedPlunder;
import yorkpirates.events.EarnedPoints;
import yorkpirates.events.Event;
import yorkpirates.objects.GameObject;

/**
 * EventReactions is responsible for reacting to Events
 * such as EarnedPoints and EarnedPlunder with onscreen
 * effects.
 */
public class EventReactions implements GameObject {
    private static final int MIN_INTERVAL = 300;

    private final Array<Timer.Task> tasks;

    private long lastTime = 0;

    public EventReactions() {
        tasks = new Array<>(true, 5);
    }

    @Override
    public void create(GameScreen game) {
        game.events.register("EarnedPoints", this::onEarnedPoints);
        game.events.register("EarnedPlunder", this::onEarnedPlunder);
        game.events.register("GameWon", this::onGameWon);
    }

    @Override
    public void update(GameScreen game, float delta) {
        // Run tasks when enough time has passed.
        if (TimeUtils.timeSinceMillis(lastTime) > MIN_INTERVAL && tasks.size > 0) {
            tasks.first().run();
            tasks.removeIndex(0);
            lastTime = TimeUtils.millis();
        }
    }

    @Override
    public void render(GameScreen game) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public int getDepth() {
        return -1500;
    }

    private void onEarnedPoints(GameScreen game, Event event) {
        tasks.add(new Timer.Task() {
            @Override
            public void run() {
                createPointsMessage(game, (EarnedPoints) event);
            }
        });
    }

    private void onEarnedPlunder(GameScreen game, Event event) {
        tasks.add(new Timer.Task() {
            @Override
            public void run() {
                createPlunderMessage(game, (EarnedPlunder) event);
            }
        });
    }

    private void createPointsMessage(GameScreen game, EarnedPoints event) {
        String message = String.format("+%d", event.amount);
        Vector2 position = new Vector2(0, 0);

        if (event.reason != null) {
            message = event.reason + " " + message;
        }

        // Variable must be final to be referenced in lambda below.
        final String finalMessage = message;
        game.addObject(new VanishingText(
                position.x, position.y, () -> finalMessage,
                0, 256, 0
        ));

        lastTime = TimeUtils.millis();
    }

    private void createPlunderMessage(GameScreen game, EarnedPlunder event) {
        final String message = String.format("+%d", event.amount);

        game.addObject(new VanishingText(
                0, 0, () -> message,
                256, 256, 0
        ));

        lastTime = TimeUtils.millis();
    }

    private void onGameWon(GameScreen game, Event event) {
        game.removeObject(this);
    }
}
