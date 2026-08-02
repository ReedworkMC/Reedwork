package dev.okaj.paper.common.command.paper;

import dev.okaj.paper.common.command.*;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;

import java.lang.reflect.Method;

public final class PaperCommandExecutor {

    private final CommandInvoker invoker;

    public PaperCommandExecutor(CommandInvoker invoker) {
        this.invoker = invoker;
    }

    public void execute(CommandDefinition definition, Method method, com.mojang.brigadier.context.CommandContext<CommandSourceStack> context) {
        CommandContext commandContext = new CommandContext(context.getSource(), new CommandArguments(context.getInput()));
        try {
            invoker.invoke(definition, method, commandContext, context);
        } catch (CommandUsageException _) {
            context.getSource().getSender().sendMessage(Component.text("Usage: " + definition.usage()));
        }
    }
}