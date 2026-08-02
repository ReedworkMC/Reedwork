package dev.okaj.paper.common.command.parameter.resolver.primitiv;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.resolver.CommandParameterResolver;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.Parameter;

public final class LongCommandParameterResolver implements CommandParameterResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(Long.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return LongArgumentType.longArg();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, ParameterDefinition parameter) {
        return brigadier.getArgument(parameter.name(), Long.class);
    }
}