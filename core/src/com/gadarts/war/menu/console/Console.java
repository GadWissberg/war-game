package com.gadarts.war.menu.console;

import com.gadarts.war.menu.console.commands.CommandResult;
import com.gadarts.war.menu.console.commands.Commands;

public interface Console {
	void insertNewLog(String text, boolean logTime);

	CommandResult notifyCommandExecution(Commands command);
}
