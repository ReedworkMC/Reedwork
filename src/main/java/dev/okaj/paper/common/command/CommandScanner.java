package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.Command;
import dev.okaj.paper.common.annotation.SubCommand;

import java.lang.reflect.Method;

public final class CommandScanner {

    public CommandDefinition scan(Object instance) {
        Class<?> clazz = instance.getClass();
        Command annotation = clazz.getAnnotation(Command.class);

        if (annotation == null) {
            throw new IllegalStateException("Missing @Command");
        }

        CommandDefinition definition = new CommandDefinition(
                instance,
                annotation
        );

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = method.getAnnotation(SubCommand.class);
                definition.addSubCommand(sub.value(), method);
            }

            if (method.getName().equals("execute")) {
                definition.execute(method);
            }
        }

        return definition;
    }
}