package org.union4dev.base.events.misc;

import org.union4dev.base.events.base.Event;

public class KeyInputEvent implements Event {

    private final int key;

    public KeyInputEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }

}
