package dev.okaj.paper.common;

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
        application.registry().registerSingleton(type, name, instance);
        return application;
    }

    public AbstractPaperApplication to(Class<? extends T> implementation) {
        application.registry().registerTransient(type, name, implementation, application.injector());
        return application;
    }

    public AbstractPaperApplication toFactory(Supplier<? extends T> supplier) {
        application.registry().registerFactory(type, name, supplier);
        return application;
    }
}
