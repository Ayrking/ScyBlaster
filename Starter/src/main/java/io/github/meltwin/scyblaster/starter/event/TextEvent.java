package io.github.meltwin.scyblaster.starter.event;

import io.github.meltwin.scyblaster.commons.events.Event;
import io.github.meltwin.scyblaster.commons.events.IEventScope;
import org.jetbrains.annotations.NotNull;

/**
 * Event for Text Update
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class TextEvent implements Event {

    private final String text;
    public TextEvent(final @NotNull String text) { this.text = text; }

    public String getText() { return this.text; }

    // Event Class
    @Override
    public IEventScope getEventScope() { return GUIScope.TEXT_EVENT; }
}
