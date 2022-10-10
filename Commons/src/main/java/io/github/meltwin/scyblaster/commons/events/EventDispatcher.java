package io.github.meltwin.scyblaster.commons.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class to dispatch events
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public final class EventDispatcher {

    private EventDispatcher() {}

    // ############ MSG ############
    private static final Logger logger = LogManager.getRootLogger();
    static final String MSG_EVENT = "Launching %s";
    static final String MSG_NO_LISTENER = "No listener for %s";
    /*
     *  #########################
     *      Event subscription
     *  #########################
     */
    private static final HashMap<IEventScope, ArrayList<EventListener>> SUB = new HashMap<>();

    /**
     * Make a subscription to a certain scope
     * @param listener the subscribing listener
     * @param scope the scope to listen
     */
    public static void subscribe(final @NotNull EventListener listener, IEventScope scope) {
        if (!SUB.containsKey(scope))
            SUB.put(scope, new ArrayList<>());
        SUB.get(scope).add(listener);
    }

    /*
     *  #########################
     *      Event dispatching
     *  #########################
     */
    /**
     * Dispatch the event to the registered listener
     * @param event the event to dispatch
     */
    public static void launch(final @NotNull Event event) {
        logger.trace(String.format(MSG_EVENT, event.getClass().getSimpleName()));

        // Dispatching the events if
        if (SUB.containsKey(event.getEventScope())) {
            for (EventListener listener: SUB.get(event.getEventScope())) {
                listener.call(event);
            }
        }
        else
            logger.warn(String.format(MSG_NO_LISTENER, event.getClass().getSimpleName()));
    }
}
