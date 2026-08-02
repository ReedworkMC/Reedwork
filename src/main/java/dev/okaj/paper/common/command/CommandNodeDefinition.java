package dev.okaj.paper.common.command;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class CommandNodeDefinition {

    private final String name;
    private final Map<String, CommandNodeDefinition> children = new HashMap<>();
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

    public Map<String, CommandNodeDefinition> children() {
        return children;
    }

    public void addChild(CommandNodeDefinition node) {
        children.put(node.name(), node);
    }
}