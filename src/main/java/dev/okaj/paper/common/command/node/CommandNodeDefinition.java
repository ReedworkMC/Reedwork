package dev.okaj.paper.common.command.node;

import dev.okaj.paper.common.command.CommandException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class CommandNodeDefinition {

    private final String name;
    private final List<CommandNodeDefinition> nodes = new ArrayList<>();
    private Method handler;

    public CommandNodeDefinition(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Method handler() {
        return handler;
    }

    public void handler(Method method) {
        this.handler = method;
    }

    public boolean hasHandler() {
        return handler != null;
    }

    public List<CommandNodeDefinition> nodes() {
        return nodes;
    }

    public CommandNodeDefinition getOrCreateNode(CommandNodeDefinition node) {
        int index = nodes.indexOf(node);

        if (index != -1) {
            CommandNodeDefinition existing = nodes.get(index);

            existing.merge(node);

            return existing;
        }

        nodes.add(node);
        return node;
    }

    public void merge(CommandNodeDefinition node) {
        if (!equals(node)) {
            throw new IllegalArgumentException(
                    "Cannot merge different nodes: '" + name + "' and '" + node.name + "'"
            );
        }

        if (node.hasHandler()) {
            if (hasHandler()) {
                throw new CommandException(
                        "Duplicate command path for node '" + node.name() + "'"
                );
            }

            handler(node.handler());
        }

        for (CommandNodeDefinition child : node.nodes()) {
            getOrCreateNode(child);
        }
    }

    public List<CommandNodeDefinition> argumentNodes() {
        List<CommandNodeDefinition> result = new ArrayList<>();

        if (isArgument()) {
            result.add(this);
        }

        for (CommandNodeDefinition node : nodes) {
            result.addAll(node.argumentNodes());
        }

        return result;
    }

    public CommandNodeDefinition getArgumentNode(int index) {
        List<CommandNodeDefinition> arguments = argumentNodes();

        if (index >= arguments.size()) {
            throw new CommandException(
                    "No argument node available at index " + index
            );
        }

        return arguments.get(index);
    }

    public boolean isArgument() {
        return this instanceof ArgumentNodeDefinition;
    }

    public boolean isLiteral() {
        return this instanceof LiteralNodeDefinition;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CommandNodeDefinition other)) {
            return false;
        }

        return getClass() == other.getClass() && name().equals(other.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), name);
    }

    @Override
    public String toString() {
        return toString("");
    }

    private String toString(String indent) {
        StringBuilder builder = new StringBuilder();

        builder.append(indent)
                .append(isArgument() ? "<" + name + ">" : name);

        if (hasHandler()) {
            builder.append(" [")
                    .append(handler.getDeclaringClass().getSimpleName())
                    .append("#")
                    .append(handler.getName())
                    .append("]");
        }

        builder.append("\n");

        for (CommandNodeDefinition node : nodes) {
            builder.append(node.toString(indent + "  "));
        }

        return builder.toString();
    }
}