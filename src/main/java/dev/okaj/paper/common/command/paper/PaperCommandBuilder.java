package dev.okaj.paper.common.command.paper;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.node.ArgumentNodeDefinition;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

public final class PaperCommandBuilder {

    private final PaperCommandExecutor executor;

    public PaperCommandBuilder(PaperCommandExecutor executor) {
        this.executor = executor;
    }

    public LiteralCommandNode<CommandSourceStack> build(CommandDefinition definition) {

        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(definition.name());

        if (!definition.permission().isBlank()) {
            root.requires(source ->
                    source.getSender().hasPermission(definition.permission())
            );
        }

        for (CommandNodeDefinition node : definition.nodes()) {
            root.then(buildNode(node, definition));
        }

        if (definition.hasExecute()) {
            root.executes(context -> {
                executor.execute(definition, definition.execute(), context);
                return 1;
            });
        }

        return root.build();
    }

    private ArgumentBuilder<CommandSourceStack, ?> buildNode(CommandNodeDefinition node, CommandDefinition definition) {
        ArgumentBuilder<CommandSourceStack, ?> builder;

        if (node instanceof ArgumentNodeDefinition argument) {
            builder = Commands.argument(argument.name(), argument.argumentType());
        } else {
            builder = Commands.literal(node.name());
        }

        if (node.hasHandler()) {
            builder.executes(context -> {
                        executor.execute(definition, node.handler(), context);
                        return 1;
                    }
            );
        }

        for (CommandNodeDefinition child : node.nodes()) {
            builder.then(buildNode(child, definition));
        }

        return builder;
    }
}