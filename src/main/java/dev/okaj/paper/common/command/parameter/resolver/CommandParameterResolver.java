package dev.okaj.paper.common.command.parameter.resolver;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;

import java.lang.reflect.Parameter;

public interface CommandParameterResolver {

    boolean supports(Parameter parameter);

    ArgumentType<?> argumentType(ParameterDefinition parameter);

    Object resolve(
            CommandContext context,
            com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier,
            CommandNodeDefinition node
    );

    default SuggestionProvider<CommandSourceStack> suggestions(ParameterDefinition parameter) {
        return null;
    }
}
