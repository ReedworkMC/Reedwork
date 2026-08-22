package dev.reedworkmc.reedwork.inject.context;

import java.util.ArrayDeque;
import java.util.Deque;

public final class CreationContext {

    private final Deque<Class<?>> stack = new ArrayDeque<>();

    public void push(Class<?> type) {
        stack.push(type);
    }

    public void pop() {
        stack.pop();
    }

    public boolean contains(Class<?> type) {
        return stack.contains(type);
    }

    public String describe(Class<?> type) {
        StringBuilder builder = new StringBuilder();

        for (Class<?> clazz : stack.reversed()) {
            builder.append(clazz.getSimpleName())
                    .append("\n ↓ \n");
        }

        builder.append(type.getSimpleName());

        return builder.toString();
    }
}