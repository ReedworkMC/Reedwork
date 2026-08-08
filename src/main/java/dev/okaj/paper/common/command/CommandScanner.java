package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.Command;
import dev.okaj.paper.common.annotation.CommandHandler;
import dev.okaj.paper.common.annotation.SubCommand;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;
import dev.okaj.paper.common.command.node.CommandNodeRegistry;
import dev.okaj.paper.common.logger.PaperLogger;

import java.lang.reflect.Method;

public final class CommandScanner {

    private final CommandMethodParser parser;
    private final CommandNodeRegistry nodeRegistry;

    public CommandScanner(CommandMethodParser parser, CommandNodeRegistry nodeRegistry) {
        this.parser = parser;
        this.nodeRegistry = nodeRegistry;
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
                nodeRegistry.register(method, node);
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