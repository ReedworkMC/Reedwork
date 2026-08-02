package dev.okaj.paper.common.command.parameter;

import dev.okaj.paper.common.command.CommandException;
import dev.okaj.paper.common.command.parameter.resolver.CommandParameterResolver;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public final class ParameterResolverRegistry {

    private final List<CommandParameterResolver> resolvers = new ArrayList<>();

    public void register(CommandParameterResolver resolver) {
        resolvers.add(resolver);
    }

    public CommandParameterResolver resolve(Parameter parameter) {

        return resolvers.stream()
                .filter(r -> r.supports(parameter))
                .findFirst()
                .orElseThrow(() ->
                        new CommandException(
                                "No ParameterResolver for "
                                        + parameter.getType().getName()
                        ));
    }

    public List<CommandParameterResolver> resolvers() {
        return resolvers;
    }
}
