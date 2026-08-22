package dev.reedworkmc.reedwork.command.parameter.resolver.primitive;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.reedworkmc.reedwork.command.CommandContext;
import dev.reedworkmc.reedwork.command.node.CommandNodeDefinition;
import dev.reedworkmc.reedwork.command.parameter.ParameterDefinition;
import dev.reedworkmc.reedwork.command.parameter.resolver.CommandParameterResolver;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import org.bukkit.scoreboard.DisplaySlot;

import java.lang.reflect.Parameter;

public class DisplaySlotCommandParameterResolver implements CommandParameterResolver {
    @Override
    public boolean supports(Parameter parameter) {
        return parameter.getType().equals(DisplaySlot.class);
    }

    @Override
    public ArgumentType<?> argumentType(ParameterDefinition parameter) {
        return ArgumentTypes.scoreboardDisplaySlot();
    }

    @Override
    public Object resolve(CommandContext context, com.mojang.brigadier.context.CommandContext<CommandSourceStack> brigadier, CommandNodeDefinition node) {
        return brigadier.getArgument(node.name(), DisplaySlot.class);
    }
}
