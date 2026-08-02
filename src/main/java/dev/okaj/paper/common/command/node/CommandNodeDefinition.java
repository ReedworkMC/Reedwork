package dev.okaj.paper.common.command.node;

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

    public void addNode(CommandNodeDefinition node) {
        nodes.add(node);
    }

    public CommandNodeDefinition findChild(String name) {
        return nodes.stream().filter(node -> node.name().equals(name)).findFirst().orElse(null);
    }

    public CommandNodeDefinition getOrCreateLiteral(String name) {
        CommandNodeDefinition existing = findChild(name);
        if (existing != null) {
            return existing;
        }
        CommandNodeDefinition node = new LiteralNodeDefinition(name);
        nodes.add(node);
        return node;
    }

    public boolean isArgument() {
        return this instanceof ArgumentNodeDefinition;
    }

    public boolean isLiteral() {
        return this instanceof LiteralNodeDefinition;
    }
}