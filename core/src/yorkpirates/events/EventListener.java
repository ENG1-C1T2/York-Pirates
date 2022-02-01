package yorkpirates.events;

import yorkpirates.GameScreen;

/**
 * An object that may subscribe to a particular event system,
 * using an EventDispatcher's register method.
 */
public interface EventListener {
    /**
     * Called when this listener receives an event.
     *
     * @param game The active GameScreen instance.
     * @param event The event instance received.
     */
    void on(GameScreen game, Event event);
}
