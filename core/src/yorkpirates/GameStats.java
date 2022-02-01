package yorkpirates;

import yorkpirates.events.*;

public class GameStats {
    private int points;
    private int plunder;
    private int completedQuests;

    public final int requiredQuests = 3;
    public final int simultaneousQuests = 2;

    public GameStats(EventDispatcher events) {
        points = 0;
        plunder = 0;
        completedQuests = 0;

        events.register("SurvivedFiveSeconds", this::onSurvivedFiveSeconds);
        events.register("KilledEnemy", this::onKilledEnemy);
        events.register("DestroyedCollege", this::onDestroyedCollege);
        events.register("CompletedQuest", this::onCompletedQuest);

        events.register("EarnedPoints", this::onEarnedPoints);
        events.register("EarnedPlunder", this::onEarnedPlunder);
    }

    public int getPoints() {
        return points;
    }

    public int getPlunder() {
        return plunder;
    }

    public int getCompletedQuests() {
        return completedQuests;
    }

    private void onEarnedPoints(GameScreen game, Event event) {
        points += ((EarnedPoints) event).amount;
    }

    private void onEarnedPlunder(GameScreen game, Event event) {
        plunder += ((EarnedPlunder) event).amount;
    }

    private void onSurvivedFiveSeconds(GameScreen game, Event event) {
        game.events.trigger(new EarnedPoints("Survived", 5));
    }

    private void onKilledEnemy(GameScreen game, Event event) {
        game.events.trigger(new EarnedPoints("Killed enemy", 25));
        game.events.trigger(new EarnedPlunder("Killed enemy", 50));
    }

    private void onDestroyedCollege(GameScreen game, Event event) {
        game.events.trigger(new EarnedPoints("Destroyed college", 100));
        game.events.trigger(new EarnedPlunder("Destroyed college", 300));
    }

    private void onCompletedQuest(GameScreen game, Event event) {
        completedQuests++;

        game.events.trigger(new EarnedPoints("Completed quest", 250));

        if (completedQuests >= requiredQuests) {
            game.events.trigger(new CompletedAllQuests());
        }
    }
}
