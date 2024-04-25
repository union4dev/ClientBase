package org.union4dev.base.module.movement;

import org.union4dev.base.Access;
import org.union4dev.base.annotations.event.EventTarget;
import org.union4dev.base.annotations.system.Disable;
import org.union4dev.base.annotations.system.Enable;
import org.union4dev.base.annotations.system.Module;
import org.union4dev.base.command.CommandManager;
import org.union4dev.base.events.update.TickEvent;
import org.union4dev.base.module.Category;
import org.union4dev.base.module.ModuleManager;
import org.union4dev.base.util.ChatUtil;
import org.union4dev.base.util.LiteInvoke;

@Module(value = "Sprint",category = Category.Movement)
public class Sprint implements Access.InstanceAccess {

    private final ModuleManager moduleManager;

    public Sprint(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    /**
     * Subscribe a {@link TickEvent}
     *
     * @param event Event
     */
    @EventTarget
    public void onUpdate(TickEvent event, CommandManager commandManager) {
        setSuffix(moduleManager.getModules().size() + " Modules and " + commandManager.getCommands().size() + " Commands!", this);

        mc.gameSettings.keyBindSprint.pressed = true;
    }

    /**
     * An Example of onEnable
     * A class can contain multiple this annotations
     */
    @Enable
    public void onEnable() {
        ChatUtil.info("Sprint Enabled.");
    }

    /**
     * An Example of onDisable
     * A class can contain multiple this annotations
     */
    @Disable
    public void onDisable() {
        ChatUtil.info("Sprint Disabled.");
    }
}
