package dev.okaj.paper.common.command.usage;

import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.node.CommandNodeDefinition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class CommandUsageGenerator {

    public String generate(CommandDefinition definition) {
        StringBuilder builder = new StringBuilder();
        builder.append("/").append(definition.name());

        appendNodes(builder, definition.nodes());

        return builder.toString();
    }

    private void appendNodes(StringBuilder builder, List<CommandNodeDefinition> nodes) {
        if (nodes.isEmpty()) {
            return;
        }

        Map<String, List<CommandNodeDefinition>> grouped = nodes.stream()
                .collect(Collectors.groupingBy(this::nodeKey));

        for (List<CommandNodeDefinition> group : grouped.values()) {
            appendGroup(builder, group);
        }
    }

    private void appendGroup(StringBuilder builder, List<CommandNodeDefinition> group) {
        CommandNodeDefinition node = group.getFirst();

        builder.append(" ")
                .append(nodeName(node));

        List<CommandNodeDefinition> children = group.stream()
                .flatMap(n -> n.nodes().stream())
                .toList();

        appendChildren(builder, children);
    }

    private void appendChildren(StringBuilder builder, List<CommandNodeDefinition> children) {
        if (children.isEmpty()) {
            return;
        }

        if (children.size() == 1) {
            appendNodes(builder, children);
            return;
        }

        Map<String, List<CommandNodeDefinition>> grouped =
                children.stream()
                        .collect(Collectors.groupingBy(this::nodeKey));

        if (grouped.size() > 1) {
            builder.append(" (");

            builder.append(
                    grouped.values().stream()
                            .map(List::getFirst)
                            .map(this::nodeName)
                            .collect(Collectors.joining("|"))
            );

            builder.append(")");

            return;
        }

        appendNodes(builder, children);
    }

    private String nodeKey(CommandNodeDefinition node) {
        return (node.isArgument() ? "ARG:" : "LIT:") + node.name();
    }

    private String nodeName(CommandNodeDefinition node) {
        return node.isArgument()
                ? "<" + node.name() + ">"
                : node.name();
    }
}