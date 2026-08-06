package dev.okaj.paper.common.command.node;

import dev.okaj.paper.common.command.CommandException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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

    public CommandNodeDefinition findChild(String name) {
        return nodes.stream().filter(node -> node.name().equals(name)).findFirst().orElse(null);
    }

    public CommandNodeDefinition getOrCreateNode(CommandNodeDefinition node) {
        int index = nodes.indexOf(node);

        if (index != -1) {
            CommandNodeDefinition existing = nodes.get(index);

            if (node.hasHandler()) {
                if (existing.hasHandler()) {
                    throw new CommandException("Duplicate command path for node '" + node.name() + "'");
                }

                existing.handler(node.handler());
            }

            for (CommandNodeDefinition child : node.nodes()) {
                existing.getOrCreateNode(child);
            }

            return existing;
        }

        nodes.add(node);
        return node;
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

        return isArgument() == other.isArgument()
                && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + (isArgument() ? 1 : 0);
    }
}