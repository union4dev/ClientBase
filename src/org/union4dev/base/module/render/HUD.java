package org.union4dev.base.module.render;

import org.union4dev.base.Access;
import org.union4dev.base.annotations.event.EventTarget;
import org.union4dev.base.annotations.system.Module;
import org.union4dev.base.annotations.system.Startup;
import org.union4dev.base.events.render.Render2DEvent;
import org.union4dev.base.module.Category;
import org.union4dev.base.module.ModuleManager;
import org.union4dev.base.module.movement.Sprint;
import org.union4dev.base.util.LiteInvoke;
import org.union4dev.base.value.impl.BooleanValue;
import org.union4dev.base.value.impl.NumberValue;

import java.util.ArrayList;
@Module(value = "HUD",category = Category.Render)
@Startup // enable when client startup
public class HUD implements Access.InstanceAccess {

    /**
     * An Example Bool value
     */
    public BooleanValue array = new BooleanValue("Array", true);

    public NumberValue spacing = new NumberValue("Spacing",3,1,5,1);


    @LiteInvoke.Autowired
    private Sprint sprint;

    @LiteInvoke.Autowired
    private ModuleManager moduleManager;

    /**
     * Subscribe a {@link Render2DEvent}
     *
     * @param event Event
     */
    @EventTarget
    public void onRender2D(Render2DEvent event) {
        access.getFontManager().F18.drawStringWithShadow("Client Base " + sprint.getClass().getName(), 4, 4, -1);
        if(array.getValue()){
            int width = event.getScaledResolution().getScaledWidth();
            int y = 4;
            ArrayList<Class<?>> enabledModules = new ArrayList<>();
            for (Class<?> m : access.getModuleManager().getModules()) {
                if (access.getModuleManager().isEnabled(m) && access.getModuleManager().isVisible(m)) {
                    enabledModules.add(m);
                }
            }
            enabledModules.sort((o1, o2) -> access.getFontManager().F18.getWidth(access.getModuleManager().format(o2)) - access.getFontManager().F18.getWidth(access.getModuleManager().format(o1)));
            for (Class<?> module : enabledModules) {
                int moduleWidth = access.getFontManager().F18.getWidth(access.getModuleManager().format(module));
                access.getFontManager().F18.drawStringWithShadow(access.getModuleManager().format(module), width - moduleWidth - 4, y, -1);
                y += access.getFontManager().F18.getHeight() + spacing.getValue();
            }
        }
    }

}
