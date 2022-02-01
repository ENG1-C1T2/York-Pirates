package yorkpirates.events;

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
