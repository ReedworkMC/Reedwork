package dev.reedworkmc.reedwork.command.paper;

import dev.reedworkmc.reedwork.command.CommandArguments;
import dev.reedworkmc.reedwork.command.CommandContext;
import dev.reedworkmc.reedwork.command.CommandDefinition;
import dev.reedworkmc.reedwork.command.CommandInvoker;
import dev.reedworkmc.reedwork.command.cooldown.CommandCooldownMessage;
import dev.reedworkmc.reedwork.command.cooldown.CommandCooldownService;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import java.lang.reflect.Method;

public final class PaperCommandExecutor {

    private final CommandInvoker invoker;
    private final CommandCooldownService cooldownService;

    public PaperCommandExecutor(CommandInvoker invoker, CommandCooldownService cooldownService) {
        this.invoker = invoker;
        this.cooldownService = cooldownService;
    }

    public boolean execute(CommandDefinition definition, Method method, com.mojang.brigadier.context.CommandContext<CommandSourceStack> context) {
        CommandContext commandContext = new CommandContext(context.getSource(), new CommandArguments(context.getInput()));

        if (commandContext.isPlayer()) {
            long remaining = cooldownService.remaining(commandContext.player().getUniqueId(), definition.name(), definition.cooldown());

            if (remaining > 0) {
                context.getSource().getSender().sendMessage(CommandCooldownMessage.create(remaining));
                return false;
            }
        }

        boolean success = invoker.invoke(definition, method, commandContext, context);
        if (!success) {

            Component message = commandContext.errorMessage();

            if (message == null) {
                message = Component.text("Usage: " + definition.usage(), NamedTextColor.RED);
            }
            context.getSource().getSender().sendMessage(message);
            return false;
        }

        if (commandContext.isPlayer()) {
            cooldownService.start(commandContext.player().getUniqueId(), definition.name());
        }

        return true;
    }
}