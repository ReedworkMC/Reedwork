package dev.okaj.paper.common.command.paper;

import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.CommandInvoker;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public final class PaperCommandExecutor {

    private final CommandInvoker invoker;

    public PaperCommandExecutor() {
        this.invoker = new CommandInvoker();
    }

    public void execute(CommandDefinition definition, CommandSourceStack source) {
        CommandContext context = new CommandContext(source.getSender());
        invoker.invoke(definition, definition.execute(), context);
    }
}