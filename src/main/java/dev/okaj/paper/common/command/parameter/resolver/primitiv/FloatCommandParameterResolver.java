package dev.okaj.paper.common.command.parameter.resolver.primitiv;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.resolver.CommandParameterResolver;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.Parameter;

public final class FloatCommandParameterResolver implements CommandParameterResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(Float.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return FloatArgumentType.floatArg();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, ParameterDefinition parameter) {
        return brigadier.getArgument(parameter.name(), Float.class);
    }
}