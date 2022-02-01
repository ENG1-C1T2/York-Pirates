package yorkpirates.events;

/**
 * An Event for when the player earns points.
 */
public class EarnedPoints extends EarnedSomething {
    public EarnedPoints(String reason, int amount) {
        super(reason, amount);
    }
}
