package dev.okaj.paper.common.command.paper;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.CommandNodeDefinition;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;

public final class PaperCommandBuilder {

    private final PaperCommandExecutor executor;

    public PaperCommandBuilder() {
        this.executor = new PaperCommandExecutor();
    }

    public LiteralCommandNode<CommandSourceStack> build(CommandDefinition definition) {

        var builder = Commands.literal(definition.name());

        if (!definition.permission().isBlank()) {
            builder.requires(source ->
                    source.getSender().hasPermission(definition.permission())
            );
        }

        if (definition.hasExecute()) {
            builder.executes(context -> {
                executor.execute(definition, definition.execute(), context.getSource());

                return 1;
            });
        }

        for (CommandNodeDefinition child : definition.children().values()) {
            builder.then(buildChild(child, definition));
        }

        return builder.build();
    }

    private LiteralArgumentBuilder<CommandSourceStack> buildChild(CommandNodeDefinition node, CommandDefinition root) {
        var builder = Commands.literal(node.name());

        if (node.handler() != null) {
            builder.executes(ctx -> {
                        executor.execute(root, node.handler(), ctx.getSource());
                        return 1;
                    }
            );
        }

        for (CommandNodeDefinition child : node.children().values()) {
            builder.then(buildChild(child, root));
        }

        return builder;
    }
}