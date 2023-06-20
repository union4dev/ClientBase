package org.union4dev.base.events.render;

import net.minecraft.client.gui.ScaledResolution;
import org.union4dev.base.events.base.Event;

public class Render2DEvent implements Event {

    private final ScaledResolution scaledResolution;
    private final float renderPartialTicks;

    public Render2DEvent(ScaledResolution scaledResolution, float renderPartialTicks) {
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
