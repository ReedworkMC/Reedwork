package dev.reedworkmc.reedwork.command.parameter.resolver;

import com.google.common.collect.Range;
import com.mojang.brigadier.arguments.ArgumentType;
import dev.reedworkmc.reedwork.command.CommandContext;
import dev.reedworkmc.reedwork.command.node.CommandNodeDefinition;
import dev.reedworkmc.reedwork.command.parameter.ParameterDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.range.IntegerRangeProvider;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class IntegerRangeProviderCommandParameterResolver implements CommandParameterResolver {
    @Override
    public boolean supports(Parameter parameter) {
        // Range<Integer>
        if (!parameter.getType().equals(Range.class)) {
            return false;
        }

        Type genericType = parameter.getParameterizedType();
        if (!(genericType instanceof ParameterizedType parameterizedType)){
            return false;
        }

        Type[] arguments = parameterizedType.getActualTypeArguments();
        return arguments.length == 1 && arguments[0].equals(Integer.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return ArgumentTypes.integerRange();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, CommandNodeDefinition node) {
        return brigadier.getArgument(node.name(), IntegerRangeProvider.class).range();
    }
}
