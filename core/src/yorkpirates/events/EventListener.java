package yorkpirates.events;

/**
 * An object that may subscribe to a particular event system,
 * using an EventDispatcher's register method.
 */
public interface EventListener {
    /**
     * Called when this listener receives an event.
     *
     * @param event The event instance received.
     */
    void on(Event event);
}
