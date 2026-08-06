package dev.okaj.paper.common.command.node;

import dev.okaj.paper.common.command.CommandException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class CommandNodeRegistry {

    private final Map<Method, CommandNodeDefinition> handlers = new HashMap<>();

    public void register(Method method, CommandNodeDefinition node) {
        CommandNodeDefinition previous = handlers.put(method, node);

        if (previous != null) {
            throw new CommandException("Method already registered: " + method);
        }
    }

    public CommandNodeDefinition get(Method method) {
        CommandNodeDefinition node = handlers.get(method);

        if (node == null) {
            throw new CommandException("No command node registered for method: " + method);
        }

        return node;
    }

    public boolean contains(Method method) {
        return handlers.containsKey(method);
    }

    public void clear() {
        handlers.clear();
    }
}