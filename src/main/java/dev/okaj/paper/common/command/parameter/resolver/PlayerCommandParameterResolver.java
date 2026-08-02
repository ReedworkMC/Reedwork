package dev.okaj.paper.common.command.parameter.resolver;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;

public class PlayerCommandParameterResolver implements CommandParameterResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(Player.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return ArgumentTypes.player();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, ParameterDefinition parameter) {
        return brigadier.getArgument(parameter.name(), Player.class);
    }
}
