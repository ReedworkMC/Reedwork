package dev.reedworkmc.reedwork.command.parameter.resolver.primitive;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import dev.reedworkmc.reedwork.command.CommandContext;
import dev.reedworkmc.reedwork.command.node.CommandNodeDefinition;
import dev.reedworkmc.reedwork.command.parameter.ParameterDefinition;
import dev.reedworkmc.reedwork.command.parameter.resolver.CommandParameterResolver;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.Parameter;

public final class IntegerCommandParameterResolver implements CommandParameterResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(Integer.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return IntegerArgumentType.integer();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, CommandNodeDefinition node) {
        return brigadier.getArgument(node.name(), Integer.class);
    }
}