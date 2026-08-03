package dev.okaj.paper.common.command.parameter.resolver;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.CommandException;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class PlayerListCommandParameterResolver implements CommandParameterResolver {
    @Override
    public boolean supports(Parameter parameter) {
        // List<Player>
        if (!parameter.getType().equals(List.class)) {
            return false;
        }

        Type genericType = parameter.getParameterizedType();
        if (!(genericType instanceof ParameterizedType parameterizedType)) {
            return false;
        }

        Type[] arguments = parameterizedType.getActualTypeArguments();
        return arguments.length == 1 && arguments[0].equals(Player.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return ArgumentTypes.players();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, ParameterDefinition parameter) {
        try {
            PlayerSelectorArgumentResolver resolver = brigadier.getArgument(parameter.name(), PlayerSelectorArgumentResolver.class);

            return resolver.resolve(context.source());
        } catch (CommandSyntaxException e) {
            throw new CommandException("Could not resolve Player argument: " + parameter.name(), e);
        }
    }
}
