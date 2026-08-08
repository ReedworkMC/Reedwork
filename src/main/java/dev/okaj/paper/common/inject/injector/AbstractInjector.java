package dev.okaj.paper.common.inject.injector;

import dev.okaj.paper.common.inject.exception.DependencyException;
import dev.okaj.paper.common.logger.PaperLogger;
import dev.okaj.paper.common.inject.factory.InstanceFactory;
import dev.okaj.paper.common.inject.processor.ClassProcessor;
import dev.okaj.paper.common.inject.registry.ServiceRegistry;
import dev.okaj.paper.common.inject.scanner.ClassScanner;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInjector implements InjectorDependencyProvider {

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
        logger.info(String.format("Scanning package %s", packageName));
        List<Class<?>> classes = scanner.scan(packageName);

        logger.info(String.format("Found %d classes", classes.size()));

        for (ClassProcessor processor : processors) {
            processor.process(classes);
        }
        logger.info(String.format("Finished processing %d classes", classes.size()));
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

    @Override
    public <T> T create(Class<T> type) {
       return factory.create(type);
    }

    public ServiceRegistry getRegistry() {
        return registry;
    }
}
