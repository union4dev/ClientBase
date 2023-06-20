package org.union4dev.base.events.render;

import org.union4dev.base.events.base.Event;

public class RenderNameTagEvent extends Event.EventCancellable {

    private String string;

    public RenderNameTagEvent(String str) {
        this.string = str;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
