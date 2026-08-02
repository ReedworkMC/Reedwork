package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.SubCommand;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;
import dev.okaj.paper.common.command.node.LiteralNodeDefinition;
import dev.okaj.paper.common.command.parameter.ParameterResolverRegistry;

import java.lang.reflect.Method;

public final class CommandMethodParser {

    private final ParameterResolverRegistry registry;

    public CommandMethodParser(ParameterResolverRegistry registry) {
        this.registry = registry;
    }

    public CommandNodeDefinition parse(Method method) {
        if (method.isAnnotationPresent(SubCommand.class)) {

            SubCommand annotation = method.getAnnotation(SubCommand.class);

            return parsePath(annotation.value(), method);
        }

        CommandNodeDefinition node = new LiteralNodeDefinition("");
        node.handler(method);
        return node;
    }

    private CommandNodeDefinition parsePath(String path, Method method) {
        String[] parts = path.split(" ");

        CommandNodeDefinition root = null;
        CommandNodeDefinition current = null;

        for (String part : parts) {
            CommandNodeDefinition next = new LiteralNodeDefinition(part);

            if (root == null) {
                root = next;
            } else {
                current.addNode(next);
            }
            current = next;
        }
        current.handler(method);
        return root;
    }

}
