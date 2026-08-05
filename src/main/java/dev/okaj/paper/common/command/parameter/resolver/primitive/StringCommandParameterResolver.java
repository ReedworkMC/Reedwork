package dev.okaj.paper.common.command.parameter.resolver.primitive;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import dev.okaj.paper.common.command.parameter.resolver.CommandParameterResolver;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.Parameter;

public class StringCommandParameterResolver implements CommandParameterResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(String.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        if (parameter.isLastArgument()) {
            return StringArgumentType.greedyString();
        }
        return StringArgumentType.string();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, ParameterDefinition parameter) {
        return StringArgumentType.getString(brigadier, parameter.name());
    }
}
