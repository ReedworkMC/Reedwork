package dev.okaj.paper.common.command.paper;

import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.okaj.paper.common.command.CommandDefinition;
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
            builder.requires(source -> source.getSender().hasPermission(definition.permission()));
        }

        if (definition.hasExecute()) {
            builder.executes(context -> {
                        executor.execute(definition, context.getSource());
                        return 1;
                    }
            );
        }

        return builder.build();
    }
}