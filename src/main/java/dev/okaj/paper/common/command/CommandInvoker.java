package dev.okaj.paper.common.command;

import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.ParameterResolverRegistry;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public final class CommandInvoker {

    private final ParameterResolverRegistry registry;

    public CommandInvoker(ParameterResolverRegistry registry) {
        this.registry = registry;
    }

    public boolean invoke(CommandDefinition definition, Method method, CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier) {
        try {
            Object[] parameters = Arrays.stream(method.getParameters())
                    .map(parameter -> resolve(parameter, context, brigadier))
                    .toArray();

            method.setAccessible(true);

            Object result = method.invoke(definition.instance(), parameters);

            if (!(result instanceof Boolean success)){
                throw new CommandException("Command method must return boolean: " + method);
            }

            return success;

        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new CommandException("Could not execute command", e);
        }
    }

    private Object[] resolveParameters(Method method, CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier) {
        return Arrays.stream(method.getParameters())
                .map(parameter -> resolve(parameter, context, brigadier))
                .toArray();
    }

    private Object resolve(Parameter parameter, CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier) {
        if (parameter.getType().equals(CommandContext.class)) {
            return context;
        }

        ParameterDefinition definition = new ParameterDefinition(parameter);
        return registry.resolve(parameter).resolve(context, brigadier, definition);
    }
}