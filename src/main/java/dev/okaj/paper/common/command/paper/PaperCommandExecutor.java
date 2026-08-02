package dev.okaj.paper.common.command.paper;

import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.CommandInvoker;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.Method;

public final class PaperCommandExecutor {

    private final CommandInvoker invoker;

    public PaperCommandExecutor(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    public void execute(CommandDefinition definition, Method method, com.mojang.brigadier.context.CommandContext<CommandSourceStack> context) {
        CommandContext commandContext = new CommandContext(context.getSource());
        invoker.invoke(definition, method, commandContext, context);
    }
}