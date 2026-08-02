package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.SubCommand;
import dev.okaj.paper.common.command.node.ArgumentNodeDefinition;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;
import dev.okaj.paper.common.command.node.LiteralNodeDefinition;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.ParameterResolverRegistry;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public final class CommandMethodParser {

    private final ParameterResolverRegistry registry;

    public CommandMethodParser(ParameterResolverRegistry registry) {
        this.registry = registry;
    }

    public CommandNodeDefinition parse(Method method) {

        String path;

        if (method.isAnnotationPresent(SubCommand.class)) {
            path = method.getAnnotation(SubCommand.class).value();
        } else {
            path = "";
        }

        CommandNodeDefinition root = null;
        CommandNodeDefinition current = null;

        String[] parts = path.isBlank() ? new String[0] : path.split(" ");

        Parameter[] parameters = method.getParameters();

        int argumentIndex = 0;

        for (String part : parts) {
            CommandNodeDefinition node;

            if (isArgument(part)) {
                Parameter parameter = findNextArgumentParameter(parameters, argumentIndex);

                argumentIndex++;

                ParameterDefinition definition = new ParameterDefinition(parameter);

                node = new ArgumentNodeDefinition(
                        parameter.getName(),
                        registry.resolve(parameter).argumentType(definition)
                );
            } else {
                node = new LiteralNodeDefinition(part);
            }

            if (root == null) {
                root = node;
            } else {
                current.addNode(node);
            }
            current = node;
        }

        if (root == null) {
            throw new CommandException("SubCommand path cannot be empty: " + method.getName() + " in " + method.getDeclaringClass());
        }

        current.handler(method);

        return root;
    }

    private boolean isArgument(String value) {
        return value.startsWith("<") && value.endsWith(">");
    }

    private Parameter findNextArgumentParameter(Parameter[] parameters, int index) {
        int found = 0;

        for (Parameter parameter : parameters) {
            if (parameter.getType().equals(CommandContext.class)) {
                continue;
            }

            if (found == index) {
                return parameter;
            }
            found++;
        }
        throw new CommandException("Missing parameter for argument");
    }

}
