/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.events;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *  Event Router for the whole app
 */
public class EventRouter {

    private static final HashMap<EventChannel, ArrayList<_EventSub>> PUB_MAP = new HashMap<>();

    /*  ==============================================================
                                Static Interface
        ==============================================================
     */
    /**
     * Add a channel to the list of existing channels
     * @param channel the channel to register
     */
    public static void registerChannel(@NotNull EventChannel channel) {
        if (!PUB_MAP.containsKey(channel))
            PUB_MAP.put(channel, new ArrayList<_EventSub>());
    }

    /**
     * Register a subscriber on a given channel
     * @param sub the subscriber that will receive the message
     */
    public static void registerSubscriber(@NotNull EventChannel channel, @NotNull _EventSub sub) {
        // Register the channel if it does not exist already
        registerChannel(channel);

        PUB_MAP.get(channel).add(sub);
    }

    /**
     * Send an event to all registered subscribers on the given channel
     * @param channel the channel onto which the event should be sent
     * @param event the event to send
     */
    public static void sendEvent(@NotNull EventChannel channel, @NotNull SoftReference<Event> event) {
        if (!PUB_MAP.containsKey(channel))
            return;

        // Send event to all subscribers
        for(_EventSub sub: PUB_MAP.get(channel))
            sub.receive_msg(channel, event);
    }

}
