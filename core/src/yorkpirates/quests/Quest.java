package yorkpirates.quests;

/**
 * A single quest - might be something like 'Kill 5 enemies', or
 * 'Survive fifty seconds'
 */
public interface Quest {
    /**
     * Checks if the quest has been completed.
     *
     * @return true if the quest is completed, false otherwise.
     */
    boolean isCompleted();

    /**
     * Display how much progress in this quest the player has made
     * so far.
     *
     * @return A string describing the player's progress.
     */
    String showProgress();
}
