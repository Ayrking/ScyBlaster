package io.github.meltwin.scyblaster.commons.events;

/**
 * Interface for custom Events
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public interface Event {
    IEventScope getEventScope();
    default void launchEvent() {
        EventDispatcher.launch(this);
    }
}
