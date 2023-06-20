package org.union4dev.base.events.render;

import org.union4dev.base.events.base.Event;

public class Render3DEvent implements Event {

    private final float renderPartialTicks;

    public Render3DEvent(float renderPartialTicks) {
        this.renderPartialTicks = renderPartialTicks;
    }

    public float getRenderPartialTicks() {
        return renderPartialTicks;
    }
}
