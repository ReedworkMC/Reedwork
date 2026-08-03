package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.inject.provider.FactoryProvider;
import dev.okaj.paper.common.inject.provider.Provider;
import dev.okaj.paper.common.inject.provider.SingletonProvider;
import dev.okaj.paper.common.inject.provider.TransientProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class ServiceRegistry {

    private final Map<Class<?>, Provider<?>> providers = new ConcurrentHashMap<>();

    public <T> void registerSingleton(Class<?> type, T instance) {
        providers.put(type, new SingletonProvider<>(instance));
    }

    public <T> void registerTransient(Class<T> type, Class<? extends T> implementation, PaperInjector injector) {
        providers.put(type, new TransientProvider<>(injector, implementation));
    }

    public <T> void registerFactory(Class<T> type, Supplier<? extends T> supplier){
        providers.put(type, new FactoryProvider<>(supplier));
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type) {
        Provider<?> provider = providers.get(type);

        if (provider == null) {
            return null;
        }

        return (T) providers.get(type).get();
    }

    public void remove(Class<?> type) {
        providers.remove(type);
    }

    public boolean contains(Class<?> type) {
        return providers.containsKey(type);
    }
}
