/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.events;

import io.meltwin.scyblaster.common.SBCommon;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.SoftReference;

/**
 * Publisher interface providing with bare method to communicate with the EventRouter
 */
public interface EventPub {

    /**
     * Publish an event on the given channel
     * @param channel the channel onto which the event should be sent
     * @param event the event to send
     */
    default void publish(@NotNull EventChannel channel, @NotNull Event event) {
        SBCommon.LOGGER.debug(String.format("Publishing message on channel %s", channel.get_name()));
        EventRouter.sendEvent(channel, new SoftReference<>(event));
    }
}
