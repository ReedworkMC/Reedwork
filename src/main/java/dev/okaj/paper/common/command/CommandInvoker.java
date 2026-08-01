package dev.okaj.paper.common.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CommandInvoker {

    public void invoke(CommandDefinition definition, Method method, CommandContext context, Object... arguments) {
        try {
            Object[] parameters = resolveParameters(method, context, arguments);

            method.setAccessible(true);
            method.invoke(definition.instance(), parameters);

        } catch (InvocationTargetException | IllegalAccessException e) {

            throw new CommandException("Could not execute command", e);
        }
    }

    private Object[] resolveParameters(Method method, CommandContext context, Object[] arguments) {
        return java.util.Arrays.stream(method.getParameterTypes())
                .map(type ->
                        resolve(type, context, arguments)
                )
                .toArray();
    }


    private Object resolve(Class<?> type, CommandContext context, Object[] arguments) {
        if (type.equals(CommandContext.class)) {
            return context;
        }

        throw new CommandException("Unknown command parameter: " + type.getName());
    }
}