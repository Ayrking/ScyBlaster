/*
 * Copyright (c) Meltwin 2023.
 *
 * Scyblaster Common is a library providing utils for MC Launcher making.
 *
 * Source: https://github.com/Meltwin/ScyBlaster
 * Distributed under the GNU Affero General Public License v3.0
 */

package io.meltwin.scyblaster.common.events;

/**
 * Public Event Subscriber to avoid circular dependencies while integrating the event system
 */
interface EventSub extends _EventSub {

    /**
     * Register to all the pre-parameterized channels
     */
    default void register() {
        for (EventChannel c: channels()) {
            EventRouter.registerSubscriber(c, this);
        }
    }
}
