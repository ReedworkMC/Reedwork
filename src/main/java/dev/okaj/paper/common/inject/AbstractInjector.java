package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.PaperLogger;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInjector {

    protected final ServiceRegistry registry;
    protected final ClassScanner scanner;
    protected final CreationContext creationContext;
    protected final List<ClassProcessor> processors = new ArrayList<>();
    protected final PaperLogger logger;
    protected final ConstructorResolver resolver;

    //todo later
    // Injector
    // │
    // ├── ServiceRegistry
    // ├── InstanceFactory
    // ├── DependencyResolver
    // └── Scanner

    protected AbstractInjector(ServiceRegistry registry, ClassScanner scanner, CreationContext creationContext, PaperLogger logger) {
        this.registry = registry;
        this.scanner = scanner;
        this.creationContext = creationContext;
        this.logger = logger;
        this.resolver = new ConstructorResolver(this);
    }

    public void addProcessor(ClassProcessor processor) {
        processors.add(processor);
    }

    public void scan(String packageName) {
        List<Class<?>> classes = scanner.scan(packageName);

        for (Class<?> clazz : classes) {
            logger.info("Found: " + clazz.getName());
        }

        for (ClassProcessor processor : processors) {
            processor.process(classes);
        }
    }

    public <T> T get(Class<T> type) {
        return get(type, "");
    }

    public <T> T get(Class<T> type, String name) {
        T instance = registry.get(type, name);
        if (instance != null) {
            return instance;
        }

        if (type.isInterface()) {
            throw new DependencyException("No binding found for interface: " + type.getName());
        }

        return create(type);
    }

    public <T> T create(Class<T> type) {
        if (creationContext.contains(type)) {
            throw new DependencyException("Circular dependency detected:\n"
                    + creationContext.describe(type));
        }

        creationContext.push(type);

        try {
            Object instance = resolver.create(type);
            Scope scope = ScopeResolver.resolve(type);

            if (scope == Scope.SINGLETON) {
                registry.registerSingleton(type, "", instance);
                return type.cast(instance);
            }

            return type.cast(instance);

        } finally {
            creationContext.pop();
        }
    }

    public ServiceRegistry getRegistry() {
        return registry;
    }
}
