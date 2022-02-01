package yorkpirates.quests;

import com.badlogic.gdx.utils.Array;
import yorkpirates.GameScreen;
import yorkpirates.events.CompletedQuest;
import yorkpirates.objects.GameObject;
import yorkpirates.ui.StringSupplier;

import java.util.Random;

/**
 * The GameObject responsible for managing the player's
 * quests, and displaying a representation of them on the screen.
 */
public class QuestManager implements GameObject {
    private static final Random RANDOM = new Random();

    private final Array<Quest> activeQuests;
    private final Array<Integer> activeQuestTypes;

    public QuestManager() {
        activeQuests = new Array<>(true, 2);
        activeQuestTypes = new Array<>(true, 2);
    }

    @Override
    public void create(GameScreen game) {

    }

    @Override
    public void update(GameScreen game, float delta) {
        for (int i = 0; i < activeQuests.size; i++) {
            Quest quest = activeQuests.get(i);

            if (quest.isCompleted()) {
                game.events.trigger(new CompletedQuest());
                activeQuests.removeIndex(i);
                activeQuestTypes.removeIndex(i);
            }
        }

        // Create new quests if there are less than 2,
        // and we haven't yet completed enough to face the final challenge.
        while (
                activeQuests.size < game.stats.simultaneousQuests &&
                activeQuests.size < game.stats.requiredQuests - game.stats.getCompletedQuests()
        ) {
            Quest newQuest = newRandomQuest(game);
            if (newQuest == null) {
                // Couldn't find a quest type that isn't already active.
                break;
            }
            activeQuests.add(newQuest);
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
        return 0;
    }

    private Quest newRandomQuest(GameScreen game) {
        final int MAX_ATTEMPTS = 100;

        int x, attempts = 0;
        do {
            x = RANDOM.nextInt(2);

            if (++attempts > MAX_ATTEMPTS) {
                return null;
            }
        } while (activeQuestTypes.contains(x, false));

        activeQuestTypes.add(x);

        switch (x) {
            case 0:
                return new KillXEnemies(game.events);
            case 1:
            default:
                return new DestroyXColleges(game.events);
        }
    }

    /**
     * Creates a new StringSupplier to be used with a Printout.
     *
     * @param game the active GameScreen.
     * @return A new StringSupplier.
     */
    public StringSupplier createSupplier(GameScreen game) {
        return new PrintoutSupplier(game);
    }

    private class PrintoutSupplier implements StringSupplier {
        private final GameScreen game;

        private PrintoutSupplier(GameScreen game) {
            this.game = game;
        }

        @Override
        public String get() {
            StringBuilder output = new StringBuilder(
                    String.format("Quests completed: %d/%d\n%d quests available:\n",
                        game.stats.getCompletedQuests(),
                        game.stats.requiredQuests,
                        activeQuests.size
                    )
            );

            for (Quest quest : activeQuests) {
                output.append(quest.showProgress()).append("\n");
            }

            return output.toString();
        }
    }
}
