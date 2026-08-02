package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.Command;
import dev.okaj.paper.common.annotation.CommandHandler;
import dev.okaj.paper.common.annotation.SubCommand;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;

import java.lang.reflect.Method;

public final class CommandScanner {

    private final CommandMethodParser parser;

    public CommandScanner(CommandMethodParser parser) {
        this.parser = parser;
    }

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
            if (method.isAnnotationPresent(CommandHandler.class) || method.isAnnotationPresent(SubCommand.class)) {
                CommandNodeDefinition node = parser.parse(method);
                definition.addNode(node);
            }
        }

        return definition;
    }
}