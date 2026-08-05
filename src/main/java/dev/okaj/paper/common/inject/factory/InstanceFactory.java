package dev.okaj.paper.common.inject.factory;

import dev.okaj.paper.common.inject.*;
import dev.okaj.paper.common.inject.context.CreationContext;
import dev.okaj.paper.common.inject.registry.ServiceRegistry;
import dev.okaj.paper.common.inject.resolver.ParameterResolver;
import dev.okaj.paper.common.inject.scope.Scope;
import dev.okaj.paper.common.inject.scope.ScopeResolver;

import java.lang.reflect.Constructor;

public final class InstanceFactory {

    private final ConstructorSelector selector;
    private final ParameterResolver parameterResolver;
    private final ObjectInstantiator instantiator;
    private final CreationContext creationContext;
    private final ServiceRegistry registry;

    public InstanceFactory(DependencyProvider provider, ServiceRegistry registry) {
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
                registry.registerSingleton(type, "", instance);
            }

            return instance;

        } finally {
            creationContext.pop();
        }
    }
}