package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.inject.provider.FactoryProvider;
import dev.okaj.paper.common.inject.provider.Provider;
import dev.okaj.paper.common.inject.provider.SingletonProvider;
import dev.okaj.paper.common.inject.provider.TransientProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class ServiceRegistry {

    private final Map<ServiceKey, Provider<?>> providers = new ConcurrentHashMap<>();

    public <T> void registerSingleton(Class<?> type, String name, T instance) {
        providers.put(new ServiceKey(type, name), new SingletonProvider<>(instance));
    }

    public <T> void registerTransient(Class<T> type, String name, Class<? extends T> implementation, PaperInjector injector) {
        providers.put(new ServiceKey(type, name), new TransientProvider<>(injector, implementation));
    }

    public <T> void registerFactory(Class<T> type, String name, Supplier<? extends T> supplier) {
        providers.put(new ServiceKey(type, name), new FactoryProvider<>(supplier));
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
