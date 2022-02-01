package yorkpirates.events;

/**
 * An Event for when the player earns either points or plunder.
 */
public class EarnedSomething implements Event {
    public final String reason;
    public final int amount;

    /**
     * Creates a new EarnedSomething Event.
     *
     * @param reason The reason they earned the things.
     * @param amount The amount of things they earned.
     */
    public EarnedSomething(String reason, int amount) {
        this.reason = reason;
        this.amount = amount;
    }
}
