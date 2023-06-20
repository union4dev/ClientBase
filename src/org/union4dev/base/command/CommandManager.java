package org.union4dev.base.command;

import org.union4dev.base.command.commands.BindCommand;
import org.union4dev.base.command.commands.ToggleCommand;
import org.union4dev.base.util.ChatUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * CommandManager, a custom command for managing clients, start with -
 *
 * @author cubk
 */
public class CommandManager {

    /**
     * Command Prefix, you can edit it
     */
    private static final String prefix = "-";
    /**
     * Command map
     */
    private final HashMap<String[], Command> commands = new HashMap<>();

    /**
     * Register commands
     */
    public CommandManager() {
        register(new ToggleCommand(), "t", "toggle");
        register(new BindCommand(), "bind");
    }

    /**
     * Register Command
     *
     * @param instance Command Instance
     * @param name     Name (and alias)
     */
    private void register(Command instance, String... name) {
        commands.put(name, instance);
    }

    /**
     * Process Command form chat
     *
     * @param rawMessage Message
     * @return Cancelled
     */
    public boolean processCommand(String rawMessage) {
        if (!rawMessage.startsWith(prefix)) {
            return false;
        }

        boolean safe = rawMessage.split(prefix).length > 1;

        if (safe) {
            String beheaded = rawMessage.split(prefix)[1];

            String[] args = beheaded.split(" ");

            Command command = getCommand(args[0]);

            if (command != null) {
                command.run(args);
            } else {
                ChatUtil.info("Try -help.");
            }
        } else {
            ChatUtil.info("Try -help.");
        }

        return true;
    }

    /**
     * Get command instance
     *
     * @param name Command name or alias
     * @return {@link Command}
     */
    private Command getCommand(String name) {
        for (Map.Entry<String[], Command> entry : commands.entrySet()) {
            String[] key = entry.getKey();
            for (String s : key) {
                if (s.equalsIgnoreCase(name)) {
                    return entry.getValue();
                }
            }

        }
        return null;
    }

    /**
     * Get command map
     *
     * @return {@link HashMap}<{@link String[]}, {@link Command}>
     */
    public HashMap<String[], Command> getCommands() {
        return commands;
    }

}
