package io.github.meltwin.scyblaster.starter.event;

import io.github.meltwin.scyblaster.commons.events.Event;
import io.github.meltwin.scyblaster.commons.events.IEventScope;

/**
 * Event for Progress Bar value update
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public class ProgressBarValueEvent implements Event {

    private final int value;
    public ProgressBarValueEvent(int new_value) {
        this.value = new_value;
    }
    public int getValue() { return value; }

    // Event Class
    @Override
    public IEventScope getEventScope() { return GUIScope.PROGRESS_EVENT; }
}
