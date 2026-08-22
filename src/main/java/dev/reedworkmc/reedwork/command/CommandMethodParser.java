package dev.reedworkmc.reedwork.command;

import dev.reedworkmc.reedwork.annotation.SubCommand;
import dev.reedworkmc.reedwork.annotation.Suggest;
import dev.reedworkmc.reedwork.command.node.ArgumentNodeDefinition;
import dev.reedworkmc.reedwork.command.node.CommandNodeDefinition;
import dev.reedworkmc.reedwork.command.node.LiteralNodeDefinition;
import dev.reedworkmc.reedwork.command.parameter.ParameterDefinition;
import dev.reedworkmc.reedwork.command.parameter.ParameterResolverRegistry;
import dev.reedworkmc.reedwork.command.parameter.ParameterSuggestion;

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

        int argumentCount = 0;

        for (String part : parts) {
            if (isArgument(part)) {
                argumentCount++;
            }
        }

        int argumentIndex = 0;

        for (String part : parts) {
            CommandNodeDefinition node;

            if (isArgument(part)) {

                String argumentName = part.substring(1, part.length() - 1);

                Parameter parameter = findNextArgumentParameter(parameters, argumentIndex);

                ParameterDefinition definition = new ParameterDefinition(parameter, argumentIndex++, argumentCount);

                ParameterSuggestion suggestion = createSuggestion(parameter);

                node = new ArgumentNodeDefinition(
                        argumentName,
                        registry.resolve(parameter).argumentType(definition),
                        suggestion
                );

            } else {
                node = new LiteralNodeDefinition(part);
            }

            if (root == null) {
                root = node;
                current = root;
            } else {
                current = current.getOrCreateNode(node);
            }
        }

        if (root == null) {
            throw new CommandException("SubCommand path cannot be empty: " + method);
        }

        current.handler(method);

        return root;
    }

    private ParameterSuggestion createSuggestion(Parameter parameter) {
        Suggest suggest = parameter.getAnnotation(Suggest.class);

        if (suggest == null) {
            return null;
        }

        return new ParameterSuggestion(suggest.value());
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
