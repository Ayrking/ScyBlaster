package io.github.meltwin.scyblaster.starter.event.listener;

import io.github.meltwin.scyblaster.commons.events.Event;
import io.github.meltwin.scyblaster.commons.events.EventListener;
import io.github.meltwin.scyblaster.commons.events.IEventScope;
import io.github.meltwin.scyblaster.starter.event.GUIScope;
import io.github.meltwin.scyblaster.starter.event.ProgressBarMaxEvent;
import io.github.meltwin.scyblaster.starter.event.ProgressBarValueEvent;
import io.github.meltwin.scyblaster.starter.event.TextEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Listener for all GUI-related events
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public interface GUIEventListener extends EventListener {

    @Override
    default IEventScope[] getScopes() {
        return new IEventScope[]{GUIScope.TEXT_EVENT, GUIScope.PROGRESS_EVENT};
    }

    @Override
    default void call(final @NotNull Event event) {
        if (event instanceof TextEvent)
            this.onTextEvent((TextEvent) event);
        if (event instanceof ProgressBarValueEvent)
            this.onProgressValueEvent((ProgressBarValueEvent) event);
        if (event instanceof ProgressBarMaxEvent)
            this.onProgressMaxEvent((ProgressBarMaxEvent) event);
    }

    void onTextEvent(final @NotNull TextEvent event);
    void onProgressValueEvent(final @NotNull ProgressBarValueEvent event);
    void onProgressMaxEvent(final @NotNull ProgressBarMaxEvent event);
}
