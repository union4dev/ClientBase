package dev.saturn.event.impl;

import dev.saturn.event.Event;
import net.minecraft.client.gui.ScaledResolution;

public class EventRender2D extends Event {

    private final ScaledResolution scaledResolution;
    private final float renderPartialTicks;

    public EventRender2D(ScaledResolution scaledResolution, float renderPartialTicks) {
        this.scaledResolution = scaledResolution;
        this.renderPartialTicks = renderPartialTicks;
    }

    public ScaledResolution getScaledResolution() {
        return scaledResolution;
    }

    public float getRenderPartialTicks() {
        return renderPartialTicks;
    }
}
