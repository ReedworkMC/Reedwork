package dev.okaj.paper.common.inject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class InstanceRegistry {

    private final Map<Class<?>, Object> instances = new ConcurrentHashMap<>();

    public void register(Class<?> type, Object instance) {
        instances.put(type, instance);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        return (T) instances.get(type);
    }

    public void remove(Class<?> type) {
        instances.remove(type);
    }

    public boolean contains(Class<?> type) {
        return instances.containsKey(type);
    }
}
