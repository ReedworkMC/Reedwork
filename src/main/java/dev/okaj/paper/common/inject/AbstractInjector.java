package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.PaperLogger;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInjector implements DependencyProvider {

    protected final ServiceRegistry registry;
    protected final ClassScanner scanner;
    protected final List<ClassProcessor> processors = new ArrayList<>();
    protected final PaperLogger logger;

    protected final InstanceFactory factory;

    protected AbstractInjector(ServiceRegistry registry, ClassScanner scanner, PaperLogger logger) {
        this.registry = registry;
        this.scanner = scanner;
        this.logger = logger;
        factory = new InstanceFactory(this, registry);
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

    @Override
    public <T> T get(Class<T> type) {
        return get(type, "");
    }

    @Override
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
       return factory.create(type);
    }

    public ServiceRegistry getRegistry() {
        return registry;
    }
}
