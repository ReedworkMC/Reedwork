package dev.okaj.paper.common.inject.provider;

import dev.okaj.paper.common.inject.PaperInjector;

public class TransientProvider<T> implements Provider<T> {

    private final PaperInjector injector;
    private final Class<T> implementation;

    public TransientProvider(PaperInjector injector, Class<T> implementation) {
        this.injector = injector;
        this.implementation = implementation;
    }

    @Override
    public T get() {
        return injector.create(implementation);
    }
}
