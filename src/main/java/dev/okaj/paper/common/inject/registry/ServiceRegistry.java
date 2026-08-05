package dev.okaj.paper.common.inject.registry;

import dev.okaj.paper.common.inject.provider.Provider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ServiceRegistry {

    private final Map<ServiceKey, Provider<?>> providers = new ConcurrentHashMap<>();

    public void register(ServiceKey key, Provider<?> provider) {
        providers.put(key, provider);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type, String name) {
        ServiceKey key = new ServiceKey(type, name);
        Provider<?> provider = providers.get(key);

        if (provider == null) {
            return null;
        }

        return (T) provider.get();
    }

    public void remove(Class<?> type) {
        providers.keySet()
                .removeIf(key -> key.type().equals(type));
    }

    public void remove(Class<?> type, String name) {
        providers.remove(new ServiceKey(type, name));
    }

    public boolean contains(Class<?> type) {
        return providers.keySet()
                .stream()
                .anyMatch(key -> key.type().equals(type));
    }

    public boolean contains(Class<?> type, String name) {
        return providers.containsKey(new ServiceKey(type, name));
    }
}
