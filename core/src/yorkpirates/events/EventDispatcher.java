package yorkpirates.events;

import yorkpirates.GameScreen;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Manages a set of EventListeners, and provides a function
 * for sending an event to all of them.
 */
public class EventDispatcher {
    private final GameScreen game;
    private final HashMap<String, HashSet<EventListener>> listeners;

    /**
     * Creates a new EventDispatcher with no listeners.
     *
     * @param game The active GameScreen.
     */
    public EventDispatcher(GameScreen game) {
        this.game = game;

        this.listeners = new HashMap<>();
    }

    /**
     * Registers a listener with this dispatcher, so it will
     * receive event callbacks triggered on this dispatcher.
     *
     * @param eventName The name of the event to listen for.
     * @param listener The listener to register.
     */
    public void register(String eventName, EventListener listener) {
        if (!listeners.containsKey(eventName)) {
            listeners.put(eventName, new HashSet<>());
        }

        listeners.get(eventName).add(listener);
    }

    /**
     * Sends an event to all listeners.
     *
     * @param event The event instance to send.
     */
    public void trigger(Event event) {
        if (listeners.containsKey(event.getName())) {
            for (EventListener listener : listeners.get(event.getName())) {
                listener.on(game, event);
            }
        }
    }
}
