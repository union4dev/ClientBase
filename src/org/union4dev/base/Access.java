package org.union4dev.base;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.union4dev.base.command.CommandManager;
import org.union4dev.base.module.ModuleManager;

/**
 * Client Entry
 * The main class where the client is loaded up.
 * Anything related to the client will start from here and managers etc instances will be stored in this class.
 *
 * @author cubk
 */
public final class Access {

    public static String CLIENT_NAME = "Client Base";

    /**
     * Client Instance, access managers with this
     */
    private static Access INSTANCE;

    /**
     * ModuleManager Instance, access modules here
     */
    private final ModuleManager moduleManager;

    /**
     * CommandManager Instance, access commands here
     */
    private final CommandManager commandManager;

    /**
     * Entry point
     */
    public Access() {
        INSTANCE = this;

        // Initialize managers
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();

        // Finished Initialization
        Display.setTitle(CLIENT_NAME);
    }

    /**
     * Get client instance
     *
     * @return {@link Access}
     */
    public static Access getInstance() {
        return INSTANCE;
    }

    /**
     * Get module manager instance
     *
     * @return {@link ModuleManager}
     */
    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    /**
     * Get command manager instance
     *
     * @return {@link CommandManager}
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * Implement this class for access instances
     *
     * @author cubk
     */
    public interface InstanceAccess {
        Minecraft mc = Minecraft.getMinecraft();
        Access access = Access.getInstance();

        default void setSuffix(String suffix, Object object) {
            access.getModuleManager().getHandle(object).setSuffix(suffix);
        }

        default void setEnable(Class<?> module, boolean state) {
            access.getModuleManager().setEnable(module, state);
        }

        default boolean isEnabled(Class<?> module) {
            return access.getModuleManager().isEnabled(module);
        }
    }

}
