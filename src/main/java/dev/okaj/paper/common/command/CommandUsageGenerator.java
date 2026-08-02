package dev.okaj.paper.common.command;

import dev.okaj.paper.common.command.node.CommandNodeDefinition;

public final class CommandUsageGenerator {

    public String generate(CommandDefinition definition) {
        StringBuilder builder = new StringBuilder();
        builder.append("/").append(definition.name());

        for (CommandNodeDefinition node : definition.nodes()) {
            append(builder, node);
        }

        return builder.toString();
    }


    private void append(StringBuilder builder, CommandNodeDefinition node) {
        if (node.isArgument()) {
            builder.append(" <").append(node.name()).append(">");
        } else {
            builder.append(" ").append(node.name());
        }

        for (CommandNodeDefinition child : node.nodes()) {
            append(builder, child);
        }
    }
}