package dev.okaj.paper.common.command.parameter.resolver.primitiv;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.resolver.CommandParameterResolver;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;

import java.lang.reflect.Parameter;
import java.util.UUID;

public class UUIDCommandParameterResolver implements CommandParameterResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(UUID.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return ArgumentTypes.uuid();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, ParameterDefinition parameter) {
        return brigadier.getArgument(parameter.name(), UUID.class);
    }
}
