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
                CommandMetadata.of(annotation)
        );

        for (Method method : clazz.getDeclaredMethods()) {

            validate(method);

            if (method.isAnnotationPresent(CommandHandler.class)) {
                definition.execute(method);
            }

            if (method.isAnnotationPresent(SubCommand.class)) {
                CommandNodeDefinition node = parser.parse(method);
                definition.addNode(node);
            }
        }

        definition.generateUsage();

        return definition;
    }

    private void validate(Method method) {
        if (!method.getReturnType().equals(Boolean.class)) {
            throw new CommandException("Command methods must return boolean: " + method);
        }
    }
}