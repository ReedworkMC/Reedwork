package dev.okaj.paper.common.command;

import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.ParameterResolverRegistry;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class CommandInvoker {

    private final ParameterResolverRegistry registry;

    public CommandInvoker(ParameterResolverRegistry registry) {
        this.registry = registry;
    }

    public boolean invoke(CommandDefinition definition, Method method, CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier) {
        try {
            Object[] parameters = resolveParameters(method, context, brigadier);

            method.setAccessible(true);

            Object result = method.invoke(definition.instance(), parameters);

            if (!(result instanceof Boolean success)){
                throw new CommandException("Command method must return boolean: " + method);
            }

            return success;

        } catch (InvocationTargetException e) {
            throw new CommandException("Command execution failed", e.getCause());
        } catch (IllegalAccessException e) {
            throw new CommandException("Could not access command method", e);
        }
    }

    private Object[] resolveParameters(Method method, CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier) {
        Parameter[] parameters = method.getParameters();

        int argumentCount = Math.toIntExact(Arrays.stream(parameters)
                .filter(parameter -> !parameter.getType().equals(CommandContext.class))
                .count());

        AtomicInteger argumentIndexes = new AtomicInteger();

        return Arrays.stream(parameters)
                .map(parameter -> {
                    if (parameter.getType().equals(CommandContext.class)) {
                        return context;
                    }

                    ParameterDefinition definition = new ParameterDefinition(parameter, argumentIndexes.getAndIncrement(), argumentCount);

                   return registry.resolve(parameter).resolve(context, brigadier, definition);

                })
                .toArray();
    }
}