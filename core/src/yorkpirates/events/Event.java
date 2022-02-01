package yorkpirates.events;

/**
 * Any event that might need to be tracked during the game,
 * objectives, for example.
 */
public interface Event {
    /**
     * Get the name of this class.
     *
     * @return The name of this class as a string.
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }
}
