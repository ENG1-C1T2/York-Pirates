package yorkpirates.events;

/**
 * An Event for when the player earns plunder.
 */
public class EarnedPlunder extends EarnedSomething {
    public EarnedPlunder(String reason, int amount) {
        super(reason, amount);
    }
}
