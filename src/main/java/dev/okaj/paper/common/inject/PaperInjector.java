package dev.okaj.paper.common.inject;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Level;

public final class PaperInjector {

    private final JavaPlugin plugin;
    private final InstanceRegistry registry;
    private final ClassScanner scanner;
    private final AnnotationProcessor processor;
    private final ConstructorResolver resolver;
    private final CreationContext creationContext;

    public PaperInjector(JavaPlugin plugin) {
        this.plugin = plugin;
        this.registry = new InstanceRegistry();
        this.scanner = new ClassScanner(plugin);
        this.processor = new AnnotationProcessor(this);
        this.resolver = new ConstructorResolver(this);
        this.creationContext = new CreationContext();

        registry.register(
                JavaPlugin.class,
                plugin
        );

        registry.register(
                plugin.getClass(),
                plugin
        );
    }

    public void scan(String packageName) {
        List<Class<?>> classes = scanner.scan(packageName);

        for (Class<?> clazz : classes) {
            plugin.getLogger().log(Level.INFO, "Found: " + clazz.getName());
        }

        processor.process(classes);
    }

    public Object createSingleton(Class<?> clazz) {
        return get(clazz);
    }

    public <T> T get(Class<T> type) {
        Object existing = registry.get(type);

        if (existing != null) {
            return type.cast(existing);
        }

        return create(type);
    }

    private <T> T create(Class<T> type) {
        if (creationContext.contains(type)) {
            throw new DependencyException("Circular dependency detected:\n"
                    + creationContext.describe(type));
        }

        creationContext.push(type);

        try {
            Object instance = resolver.create(type);
            registry.register(type, instance);
            return type.cast(instance);

        } finally {
            creationContext.pop();
        }
    }

    public InstanceRegistry getRegistry() {
        return registry;
    }
}