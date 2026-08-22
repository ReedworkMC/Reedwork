package dev.reedworkmc.reedwork.command.parameter.resolver;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.reedworkmc.reedwork.command.CommandContext;
import dev.reedworkmc.reedwork.command.CommandException;
import dev.reedworkmc.reedwork.command.node.CommandNodeDefinition;
import dev.reedworkmc.reedwork.command.parameter.ParameterDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.EntitySelectorArgumentResolver;
import org.bukkit.entity.Entity;

import java.lang.reflect.Parameter;

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
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, CommandNodeDefinition node) {
        try {
            EntitySelectorArgumentResolver resolver = brigadier.getArgument(node.name(), EntitySelectorArgumentResolver.class);

            return resolver.resolve(context.source()).getFirst();
        } catch (CommandSyntaxException e) {
            throw new CommandException("Could not resolve Entity argument: " + node.name(), e);
        }
    }
}
