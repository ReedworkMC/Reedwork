package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.Command;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class CommandDefinition {

    private final Object instance;
    private final Command annotation;
    private final Map<String, Method> subCommands = new HashMap<>();
    private Method execute;

    public CommandDefinition(Object instance, Command annotation) {
        this.instance = instance;
        this.annotation = annotation;
    }

    public Object instance() {
        return instance;
    }

    public Command annotation() {
        return annotation;
    }

    public Method execute() {
        return execute;
    }

    public void execute(Method method) {
        this.execute = method;
    }

    public Map<String, Method> subCommands() {
        return subCommands;
    }

    public void addSubCommand(String name, Method method) {
        subCommands.put(name, method);
    }

    public boolean hasExecute() {
        return execute != null;
    }

    public Method getSubCommand(String name) {
        return subCommands.get(name);
    }
}