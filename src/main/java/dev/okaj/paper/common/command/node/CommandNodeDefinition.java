package dev.okaj.paper.common.command.node;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommandNodeDefinition {

    private final String name;
    private final List<CommandNodeDefinition> children = new ArrayList<>();
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

    public List<CommandNodeDefinition> children() {
        return children;
    }

    public void addChild(CommandNodeDefinition node) {
        children.add(node);
    }
}