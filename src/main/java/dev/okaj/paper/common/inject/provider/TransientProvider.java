package dev.okaj.paper.common.inject.provider;

import dev.okaj.paper.common.inject.injector.InjectorDependencyProvider;

public class TransientProvider<T> implements Provider<T> {

    private final InjectorDependencyProvider provider;
    private final Class<T> implementation;

    public TransientProvider(InjectorDependencyProvider provider, Class<T> implementation) {
        this.provider = provider;
        this.implementation = implementation;
    }

    @Override
    public T get() {
        return provider.create(implementation);
    }
}
