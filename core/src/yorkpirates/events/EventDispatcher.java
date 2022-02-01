package yorkpirates.events;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Manages a set of EventListeners, and provides a function
 * for sending an event to all of them.
 */
public class EventDispatcher {
    private final HashMap<Event, HashSet<EventListener>> listeners;

    /**
     * Creates a new EventDispatcher with no listeners.
     */
    public EventDispatcher() {
        this.listeners = new HashMap<>();
    }

    /**
     * Registers a listener with this dispatcher, so it will
     * receive event callbacks triggered on this dispatcher.
     *
     * @param event The event to listen for.
     * @param listener The listener to register.
     */
    public void register(Event event, EventListener listener) {
        if (!listeners.containsKey(event)) {
            listeners.put(event, new HashSet<>());
        }

        listeners.get(event).add(listener);
    }

    /**
     * Sends an event to all listeners.
     *
     * @param event The event instance to send.
     */
    public void trigger(Event event) {
        for (EventListener listener : listeners.get(event)) {
            listener.on(event);
        }
    }
}
