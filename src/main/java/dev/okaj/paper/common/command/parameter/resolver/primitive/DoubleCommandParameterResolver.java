package dev.okaj.paper.common.command.parameter.resolver.primitive;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.resolver.CommandParameterResolver;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.Parameter;

public final class DoubleCommandParameterResolver implements CommandParameterResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(Double.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return DoubleArgumentType.doubleArg();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, CommandNodeDefinition node) {
        return brigadier.getArgument(node.name(), Double.class);
    }
}