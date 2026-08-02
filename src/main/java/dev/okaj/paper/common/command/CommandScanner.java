package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.Command;
import dev.okaj.paper.common.annotation.CommandHandler;
import dev.okaj.paper.common.annotation.SubCommand;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;

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
                annotation.value(),
                annotation.description(),
                annotation.permission(),
                annotation.aliases()
        );

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = method.getAnnotation(SubCommand.class);
                registerSubCommand(definition, sub.value(), method);
            }

            if (method.isAnnotationPresent(CommandHandler.class)) {
                definition.execute(method);
            }
        }

        return definition;
    }

    private void registerSubCommand(CommandDefinition definition, String path, Method method) {
        String[] parts = path.split(" ");

        CommandNodeDefinition current = null;

        for (String part : parts) {
            CommandNodeDefinition next = new CommandNodeDefinition(part);

            if (current == null) {
                definition.addSubCommand(next);
            }
            else {
                current.addChild(next);
            }

            current = next;
        }

        current.handler(method);
    }
}