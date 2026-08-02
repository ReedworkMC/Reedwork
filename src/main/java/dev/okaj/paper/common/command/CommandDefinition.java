package dev.okaj.paper.common.command;

import dev.okaj.paper.common.command.node.CommandNodeDefinition;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class CommandDefinition {

    private final Object instance;

    private final String name;
    private final String description;
    private final String permission;
    private final String[] aliases;

    private final List<CommandNodeDefinition> nodes = new ArrayList<>();
    private Method execute;

    public CommandDefinition(Object instance, String name, String description, String permission, String[] aliases) {
        this.instance = instance;
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.aliases = aliases;
    }

    public Object instance() {
        return instance;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String permission() {
        return permission;
    }

    public String[] aliases() {
        return aliases;
    }

    public Method execute() {
        return execute;
    }

    public void execute(Method method) {
        this.execute = method;
    }

    public List<CommandNodeDefinition> nodes() {
        return nodes;
    }

    public void addNode(CommandNodeDefinition node) {
        nodes.add(node);
    }

    public boolean hasExecute() {
        return execute != null;
    }
}