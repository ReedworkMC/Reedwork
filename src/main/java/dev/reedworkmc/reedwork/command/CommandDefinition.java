package dev.reedworkmc.reedwork.command;

import dev.reedworkmc.reedwork.command.node.CommandNodeDefinition;
import dev.reedworkmc.reedwork.command.usage.CommandUsageGenerator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class CommandDefinition {

    private final Object instance;

    private final CommandMetadata metadata;
    private final List<CommandNodeDefinition> nodes = new ArrayList<>();
    private String usage;
    private Method execute;

    public CommandDefinition(Object instance, CommandMetadata metadata) {
        this.instance = instance;
        this.metadata = metadata;
    }

    public Object instance() {
        return instance;
    }

    public String name() {
        return metadata.name();
    }

    public String description() {
        return metadata.description();
    }

    public String permission() {
        return metadata.permission();
    }

    public String usage() {
        return usage;
    }

    public String[] aliases() {
        return metadata.aliases();
    }

    public long cooldown() {
        return metadata.cooldown();
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
        int index = nodes.indexOf(node);

        if (index == -1) {
            nodes.add(node);
            return;
        }

        CommandNodeDefinition existing = nodes.get(index);

        existing.merge(node);
    }

    public CommandNodeDefinition rootNode(Method method) {
        for (CommandNodeDefinition node : nodes) {
            if (containsHandler(node, method)) {
                return node;
            }
        }

        throw new CommandException("No command node registered for method " + method);
    }

    private boolean containsHandler(CommandNodeDefinition node, Method method) {
        if (node.handler() == method) {
            return true;
        }

        for (CommandNodeDefinition child : node.nodes()) {
            if (containsHandler(child, method)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasExecute() {
        return execute != null;
    }

    public void generateUsage() {
        if (metadata.usage() != null && !metadata.usage().isBlank()) {
            usage = metadata.usage();
            return;
        }
        usage = new CommandUsageGenerator().generate(this);
    }
}