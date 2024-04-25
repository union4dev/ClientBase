package org.union4dev.base.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.union4dev.base.Access;
import org.union4dev.base.Initializer;
import org.union4dev.base.annotations.system.Command;
import org.union4dev.base.util.ChatUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


/**
 * CommandManager, a custom command for managing clients, start with -
 *
 * @author cubk
 */
public class CommandManager implements Initializer {

    /**
     * Command Prefix, you can edit it
     */
    private static final String prefix = "-";
    /**
     * Command map
     */
    private final HashMap<String[], CommandHandle> commands = new HashMap<>();
    private final Logger logger = LogManager.getLogger("Command Manager");

    /**
     * Register commands
     */
    public void init() {
        initialize(clazz -> {
            if(clazz.isAnnotationPresent(Command.class)){
                Command command = clazz.getAnnotation(Command.class);
                Object inst = null;
                try {
                    inst = Access.getInstance().getInvoke().createInstance(clazz);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Object finalInst = inst;
                try {
                    assert finalInst != null;
                    Access.getInstance().getInvoke().autoWired(finalInst);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Access.getInstance().getInvoke().registerBean(finalInst);
                CommandHandle handle = new CommandHandle() {

                    @Override
                    public void run(String[] args) {
                        for (Method handler : getHandlers()) {
                            try {
                                Access.getInstance().getInvoke().invokeMethod(finalInst,handler,args);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public String usage() {
                        return command.usage();
                    }
                };

                for (Method method : clazz.getDeclaredMethods()) {
                    if(method.isAnnotationPresent(Command.Handler.class)){
                        if(method.getParameters().length > 0){
                            if(method.getParameters()[0].getType() == String[].class){
                                handle.getHandlers().add(method);
                            }else {
                                logger.warn("Command class {} method {} has wrong parameters",clazz.getName(),method.getName());
                            }
                        }else {
                            logger.warn("Command class {} method {} has no parameters",clazz.getName(),method.getName());
                        }
                    }
                }

                if(handle.getHandlers().size() > 0){
                    register(handle,command.value());
                }
            }
        });
    }

    /**
     * Register Command
     *
     * @param instance Command Instance
     * @param name     Name (and alias)
     */
    private void register(CommandHandle instance, String... name) {
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

            CommandHandle commandHandle = getCommand(args[0]);

            if (commandHandle != null) {
                commandHandle.run(args);
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
     * @return {@link CommandHandle}
     */
    private CommandHandle getCommand(String name) {
        for (Map.Entry<String[], CommandHandle> entry : commands.entrySet()) {
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
     * @return {@link HashMap}<{@link String[]}, {@link CommandHandle}>
     */
    public HashMap<String[], CommandHandle> getCommands() {
        return commands;
    }

}
