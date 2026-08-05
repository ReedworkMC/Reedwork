package dev.okaj.paper.common.command.usage;

import dev.okaj.paper.common.command.CommandException;

public final class CommandUsageException extends CommandException {

    public CommandUsageException(String message) {
        super(message);
    }
}
