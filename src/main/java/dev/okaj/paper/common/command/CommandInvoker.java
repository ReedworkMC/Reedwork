package dev.okaj.paper.common.command;

import dev.okaj.paper.common.command.node.CommandNodeRegistry;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.ParameterResolverRegistry;
import dev.okaj.paper.common.logger.PaperLogger;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public final class CommandInvoker {

    private final ParameterResolverRegistry resolverRegistry;
    private final CommandNodeRegistry nodeRegistry;
    private final PaperLogger logger;

    public CommandInvoker(ParameterResolverRegistry resolverRegistry, CommandNodeRegistry nodeRegistry, PaperLogger logger) {
        this.resolverRegistry = resolverRegistry;
        this.nodeRegistry = nodeRegistry;
        this.logger = logger;
    }

    public boolean invoke(CommandDefinition definition, Method method, CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier) {
        try {
            Object[] parameters = resolveParameters(method, context, brigadier);

            method.setAccessible(true);

            Object result = method.invoke(definition.instance(), parameters);

            if (!(result instanceof Boolean success)) {
                throw new CommandException("Command method must return boolean: " + method);
            }

            return success;

        } catch (InvocationTargetException e) {
            throw new CommandException("Command execution failed", e.getCause());
        } catch (IllegalAccessException e) {
            throw new CommandException("Could not access command method", e);
        } catch (CommandException e) {
            // Parsing Failed
            logger.error("Parsing Command: " + method.getClass().getName() + " failed.", e);
            return false;
        }
    }

    private Object[] resolveParameters(Method method, CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier) {
        Parameter[] parameters = method.getParameters();

        return Arrays.stream(parameters)
                .map(parameter -> {
                    if (parameter.getType().equals(CommandContext.class)) {
                        return context;
                    }

                    return resolverRegistry.resolve(parameter)
                            .resolve(context, brigadier, nodeRegistry.get(method));

                })
                .toArray();
    }
}