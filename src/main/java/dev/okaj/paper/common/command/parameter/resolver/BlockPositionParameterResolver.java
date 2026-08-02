package dev.okaj.paper.common.command.parameter.resolver;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.CommandException;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.BlockPositionResolver;
import io.papermc.paper.math.BlockPosition;

import java.lang.reflect.Parameter;

public class BlockPositionParameterResolver implements CommandParameterResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(BlockPosition.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return ArgumentTypes.blockPosition();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, ParameterDefinition parameter) {
        try {
            BlockPositionResolver resolver = brigadier.getArgument(parameter.name(), BlockPositionResolver.class);

            return resolver.resolve(context.source());
        } catch (CommandSyntaxException e) {
            throw new CommandException("Could not resolve Block Position argument: " + parameter.name(), e);
        }
    }
}
