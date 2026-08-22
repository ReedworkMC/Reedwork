package dev.reedworkmc.reedwork.app;

import dev.reedworkmc.reedwork.inject.registry.ServiceKey;
import dev.reedworkmc.reedwork.inject.provider.FactoryProvider;
import dev.reedworkmc.reedwork.inject.provider.SingletonProvider;
import dev.reedworkmc.reedwork.inject.provider.TransientProvider;

import java.util.function.Supplier;

public class Binder<T> {

    private final AbstractReedworkApplication application;
    private final Class<T> type;

    private String name;

    public Binder(AbstractReedworkApplication application, Class<T> type) {
        this.application = application;
        this.type = type;
    }

    public Binder<T> named(String name) {
        this.name = name;
        return this;
    }

    public AbstractReedworkApplication toSingleton(T instance) {
        application.registry().register(new ServiceKey(type, name), new SingletonProvider<>(instance));
        return application;
    }

    public AbstractReedworkApplication to(Class<? extends T> implementation) {
        application.registry().register(new ServiceKey(type, name), new TransientProvider<>(application.injector(), implementation));
        return application;
    }

    public AbstractReedworkApplication toFactory(Supplier<? extends T> supplier) {
        application.registry().register(new ServiceKey(type, name), new FactoryProvider<>(supplier));
        return application;
    }
}
