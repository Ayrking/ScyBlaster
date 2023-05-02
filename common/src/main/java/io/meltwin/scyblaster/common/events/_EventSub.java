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

/**
 * Private Event Subscriber properties
 */
interface _EventSub {

    /**
     * Callback when an event is received
     * @param channel the channel from which the event arrives
     * @param event the event that has been passed down
     */
    void receive_msg(@NotNull EventChannel channel, @NotNull SoftReference<Event> event);
    ArrayList<EventChannel> channels();
}
