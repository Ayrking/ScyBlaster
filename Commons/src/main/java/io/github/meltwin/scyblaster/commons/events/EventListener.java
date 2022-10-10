package io.github.meltwin.scyblaster.commons.events;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for custom Listener
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public interface EventListener {
    IEventScope[] getScopes();
    default void register() {
        for (IEventScope scope : getScopes())
            EventDispatcher.subscribe(this, scope);
    }
    void call(final @NotNull Event event);
}
