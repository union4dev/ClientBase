package org.union4dev.base.events.misc;

import net.minecraft.util.MovingObjectPosition;
import org.union4dev.base.events.base.Event;

public class MiddleClickEvent implements Event {
    private final MovingObjectPosition objectPosition;

    public MiddleClickEvent(MovingObjectPosition mouseOver) {
        this.objectPosition = mouseOver;
    }

    public MovingObjectPosition getObjectPosition() {
        return objectPosition;
    }
}
