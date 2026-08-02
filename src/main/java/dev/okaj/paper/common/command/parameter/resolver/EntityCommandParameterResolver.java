package dev.okaj.paper.common.command.parameter.resolver;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.okaj.paper.common.command.CommandContext;
import dev.okaj.paper.common.command.CommandException;
import dev.okaj.paper.common.command.parameter.ParameterDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.EntitySelectorArgumentResolver;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Parameter;
import java.util.List;

public class EntityCommandParameterResolver implements CommandParameterResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(Entity.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return ArgumentTypes.entity();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, ParameterDefinition parameter) {
        try {
            EntitySelectorArgumentResolver resolver = brigadier.getArgument(parameter.name(), EntitySelectorArgumentResolver.class);

            return resolver.resolve(context.source()).getFirst();
        } catch (CommandSyntaxException e) {
            throw new CommandException("Could not resolve entity argument: " + parameter.name(), e);
        }
    }
}
