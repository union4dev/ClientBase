package org.union4dev.base.module.render;

import org.union4dev.base.Access;
import org.union4dev.base.annotations.event.EventTarget;
import org.union4dev.base.annotations.module.Startup;
import org.union4dev.base.events.render.Render2DEvent;
import org.union4dev.base.value.impl.BooleanValue;

import java.util.ArrayList;

@Startup // enable when client startup
public class HUD implements Access.InstanceAccess {

    /**
     * An Example Bool value
     */
    public BooleanValue array = new BooleanValue("Module Array", true);

    /**
     * Subscribe a {@link Render2DEvent}
     *
     * @param event Event
     */
    @EventTarget
    public void onRender2D(Render2DEvent event) {
        mc.fontRendererObj.drawStringWithShadow("Client Base", 4, 4, -1);

        int width = event.getScaledResolution().getScaledWidth();
        int y = 4;
        ArrayList<Class<?>> enabledModules = new ArrayList<>();
        for (Class<?> m : access.getModuleManager().getModules()) {
            if (access.getModuleManager().isEnabled(m)) {
                enabledModules.add(m);
            }
        }
        enabledModules.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(access.getModuleManager().format(o2)) - mc.fontRendererObj.getStringWidth(access.getModuleManager().format(o1)));
        for (Class<?> module : enabledModules) {
            int moduleWidth = mc.fontRendererObj.getStringWidth(access.getModuleManager().format(module));
            mc.fontRendererObj.drawStringWithShadow(access.getModuleManager().format(module), width - moduleWidth - 4, y, -1);
            y += mc.fontRendererObj.FONT_HEIGHT + 3;
        }
    }

}
