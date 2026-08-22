package dev.reedworkmc.reedwork.inject.provider;

import dev.reedworkmc.reedwork.inject.injector.InjectorDependencyProvider;

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
