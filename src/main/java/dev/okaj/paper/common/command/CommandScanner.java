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

        CommandDefinition definition = new CommandDefinition(
                instance,
                CommandMetadata.of(annotation)
        );

        boolean handlerFound = false;

        for (Method method : clazz.getDeclaredMethods()) {

            if (method.isAnnotationPresent(CommandHandler.class)) {
                if(handlerFound){
                    throw new CommandException("CommandHandler can only be declared once: " + clazz.getName());
                }

                validate(method);
                definition.execute(method);

                handlerFound = true;
            }

            if (method.isAnnotationPresent(SubCommand.class)) {
                validate(method);
                CommandNodeDefinition node = parser.parse(method);
                definition.addNode(node);
            }
        }

        definition.generateUsage();

        return definition;
    }

    private void validate(Method method) {
        Class<?> returnType = method.getReturnType();

        if (returnType != boolean.class && returnType != Boolean.class) {
            throw new CommandException("Command methods must return boolean: " + method);
        }
    }
}