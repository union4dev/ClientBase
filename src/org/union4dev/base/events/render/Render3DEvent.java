package org.union4dev.klazz.event.implement;

import org.union4dev.klazz.event.misc.Event;

public class Render3DEvent extends Event {

    private final float renderPartialTicks;

    public Render3DEvent(float renderPartialTicks) {
        this.renderPartialTicks = renderPartialTicks;
    }

    public float getRenderPartialTicks() {
        return renderPartialTicks;
    }
}
