package org.union4dev.base.command.commands;

import org.lwjgl.input.Keyboard;
import org.union4dev.base.Access;
import org.union4dev.base.command.Command;
import org.union4dev.base.util.ChatUtil;

public class BindCommand implements Command, Access.InstanceAccess {

	@Override
	public void run(String[] args) {
		if (args.length == 3) {
			Class<?> module = access.getModuleManager().getModuleClass(args[1]);
			if (module != null) {
				access.getModuleManager().setKey(module, Keyboard.getKeyIndex(args[2].toUpperCase()));
				ChatUtil.info(access.getModuleManager().getName(module) + " has been bound to " + args[2] + ".");
			} else {
				ChatUtil.info(args[1] + " not found.");
			}
		}
	}

	@Override
	public String usage() {
		return "bind <module> <key>";
	}

}
