package dev.okaj.paper.common.inject.provider;

import dev.okaj.paper.common.inject.DependencyProvider;

public class TransientProvider<T> implements Provider<T> {

    private final DependencyProvider provider;
    private final Class<T> implementation;

    public TransientProvider(DependencyProvider provider, Class<T> implementation) {
        this.provider = provider;
        this.implementation = implementation;
    }

    @Override
    public T get() {
        return provider.create(implementation);
    }
}
