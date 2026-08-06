package dev.okaj.paper.common.command.parameter.resolver;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.CommandException;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.FinePositionResolver;
import io.papermc.paper.math.FinePosition;

import java.lang.reflect.Parameter;

public class FinePositionParameterResolver implements CommandParameterResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(FinePosition.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return ArgumentTypes.finePosition();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, CommandNodeDefinition node) {
        try {
            FinePositionResolver resolver = brigadier.getArgument(node.name(), FinePositionResolver.class);

            return resolver.resolve(context.source());
        } catch (CommandSyntaxException e) {
            throw new CommandException("Could not resolve FinePosition argument: " + node.name(), e);
        }
    }
}
