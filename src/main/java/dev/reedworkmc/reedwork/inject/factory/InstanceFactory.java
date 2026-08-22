package dev.reedworkmc.reedwork.inject.factory;

import dev.reedworkmc.reedwork.inject.context.CreationContext;
import dev.reedworkmc.reedwork.inject.exception.DependencyException;
import dev.reedworkmc.reedwork.inject.injector.InjectorDependencyProvider;
import dev.reedworkmc.reedwork.inject.provider.SingletonProvider;
import dev.reedworkmc.reedwork.inject.registry.ServiceKey;
import dev.reedworkmc.reedwork.inject.registry.ServiceRegistry;
import dev.reedworkmc.reedwork.inject.resolver.ParameterResolver;
import dev.reedworkmc.reedwork.inject.scope.Scope;
import dev.reedworkmc.reedwork.inject.scope.ScopeResolver;

import java.lang.reflect.Constructor;

public final class InstanceFactory {

    private final ConstructorSelector selector;
    private final ParameterResolver parameterResolver;
    private final ObjectInstantiator instantiator;
    private final CreationContext creationContext;
    private final ServiceRegistry registry;

    public InstanceFactory(InjectorDependencyProvider provider, ServiceRegistry registry) {
        this.selector = new ConstructorSelector();
        this.parameterResolver = new ParameterResolver(provider);
        this.instantiator = new ObjectInstantiator();
        this.creationContext = new CreationContext();
        this.registry = registry;
    }

    public <T> T create(Class<T> type) {
        if (creationContext.contains(type)) {
            throw new DependencyException(
                    "Circular dependency detected:\n"
                            + creationContext.describe(type)
            );
        }

        creationContext.push(type);

        try {

            Constructor<?> constructor = selector.select(type);

            Object[] parameters = parameterResolver.resolve(constructor);

            T instance = type.cast(instantiator.instantiate(constructor, parameters));

            if (ScopeResolver.resolve(type) == Scope.SINGLETON) {
                registry.register(new ServiceKey(type, ""), new SingletonProvider<>(instance));
            }

            return instance;

        } finally {
            creationContext.pop();
        }
    }
}