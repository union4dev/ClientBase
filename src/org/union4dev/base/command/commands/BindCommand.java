package org.union4dev.base.command.commands;

import org.lwjgl.input.Keyboard;
import org.union4dev.base.Access;
import org.union4dev.base.annotations.system.Command;
import org.union4dev.base.module.movement.Sprint;
import org.union4dev.base.util.ChatUtil;

@Command(value = "bind",usage = "bind <module> <key>")
public class BindCommand implements Access.InstanceAccess {

	@Command.Handler
	public void secondHandler(String[] args, Sprint sprint) {
		ChatUtil.info("Received " + args.length + " parameter(s)!");
		ChatUtil.info("Sprint class is " + sprint.getClass().getName());
	}

	@Command.Handler
	public void run(String[] args) {
		if (args.length == 3) {
			Class<?> module = access.getModuleManager().getModuleClass(args[1]);
			if (module != null) {
				access.getModuleManager().setKey(module, Keyboard.getKeyIndex(args[2].toUpperCase()));
				ChatUtil.info(access.getModuleManager().getName(module) + " has been bound to " + args[2] + ".");
			} else {
				ChatUtil.info(args[1] + " not found.");
			}
		}else {
			ChatUtil.info(usage(this));
		}
	}
}
