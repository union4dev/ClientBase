package org.union4dev.base.command;

import org.union4dev.base.util.ChatUtil;

/**
 * Abstract Command
 *
 * @author cubk
 */
public interface Command {

	void run(String[] args);

	String usage();

	default void printUsage() {
		ChatUtil.info(usage());
	}
}
