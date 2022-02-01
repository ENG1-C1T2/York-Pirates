package yorkpirates.objects;

import com.badlogic.gdx.utils.Timer;
import yorkpirates.GameScreen;
import yorkpirates.events.SurvivedFiveSeconds;

/**
 * A GameObject that triggers the
 * SurvivedFiveSeconds Event every five seconds.
 */
public class SurvivalTimer implements GameObject {
    @Override
    public void create(GameScreen game) {
        Timer timer = new Timer();
        // Task repeats forever if repeatCount is negative.
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                game.events.trigger(new SurvivedFiveSeconds());
            }
        }, 5, 5, -1);
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
