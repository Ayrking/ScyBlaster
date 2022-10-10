package io.github.meltwin.scyblaster.starter.event;

import io.github.meltwin.scyblaster.commons.events.IEventScope;

/**
 * Event scope for GUI-related events
 * @Copyright: GNU APGLv3 - (C) 2022 Meltwin
 * @author Meltwin
 * @since 0.1-SNAPSHOT
 */
public enum GUIScope implements IEventScope {
    TEXT_EVENT,
    PROGRESS_EVENT
}
