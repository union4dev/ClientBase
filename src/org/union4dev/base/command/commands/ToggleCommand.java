package org.union4dev.base.command.commands;

import org.union4dev.base.Access;
import org.union4dev.base.command.Command;
import org.union4dev.base.util.ChatUtil;

public class ToggleCommand implements Access.InstanceAccess, Command {
    @Override
    public void run(String[] args) {
        if (args.length == 2) {
            Class<?> module = access.getModuleManager().getModuleClass(args[1]);
            if (module != null) {
                boolean state = access.getModuleManager().toggle(module);
                ChatUtil.info("Module has been " + (state ? "Enabled" : "Disabled") + "!");
            } else {
                ChatUtil.info("Module not found");
            }
        } else {
            printUsage();
        }
    }

    @Override
    public String usage() {
        return "toggle <module>";
    }
}
