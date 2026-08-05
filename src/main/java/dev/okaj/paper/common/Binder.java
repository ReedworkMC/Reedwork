package dev.okaj.paper.common;

import dev.okaj.paper.common.inject.ServiceKey;
import dev.okaj.paper.common.inject.provider.FactoryProvider;
import dev.okaj.paper.common.inject.provider.SingletonProvider;
import dev.okaj.paper.common.inject.provider.TransientProvider;

import java.util.function.Supplier;

public class Binder<T> {

    private final AbstractPaperApplication application;
    private final Class<T> type;

    private String name;

    public Binder(AbstractPaperApplication application, Class<T> type) {
        this.application = application;
        this.type = type;
    }

    public Binder<T> named(String name) {
        this.name = name;
        return this;
    }

    public AbstractPaperApplication toSingleton(T instance) {
        application.registry().register(new ServiceKey(type, name), new SingletonProvider<>(instance));
        return application;
    }

    public AbstractPaperApplication to(Class<? extends T> implementation) {
        application.registry().register(new ServiceKey(type, name), new TransientProvider<>(application.injector(), implementation));
        return application;
    }

    public AbstractPaperApplication toFactory(Supplier<? extends T> supplier) {
        application.registry().register(new ServiceKey(type, name), new FactoryProvider<>(supplier));
        return application;
    }
}
