package dev.okaj.paper.common.inject;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class PaperInjector {

    private final JavaPlugin plugin;
    private final InstanceRegistry registry;
    private final ClassScanner scanner;
    private final ConstructorResolver resolver;
    private final CreationContext creationContext;

    private final List<ClassProcessor> processors = new ArrayList<>();

    public PaperInjector(JavaPlugin plugin) {
        this.plugin = plugin;
        this.registry = new InstanceRegistry();
        this.scanner = new ClassScanner(plugin);
        this.resolver = new ConstructorResolver(this);
        this.creationContext = new CreationContext();

        registry.register(JavaPlugin.class, plugin);

        registry.register(plugin.getClass(), plugin);
    }

    public void addProcessor(ClassProcessor processor) {
        processors.add(processor);
    }

    public void scan(String packageName) {
        List<Class<?>> classes = scanner.scan(packageName);

        for (Class<?> clazz : classes) {
            plugin.getLogger().log(Level.INFO, "Found: " + clazz.getName());
        }

        for (ClassProcessor processor : processors) {
            processor.process(classes);
        }
    }

    public <T> T get(Class<T> type) {
        T instance = registry.get(type);
        if (instance != null) {
            return instance;
        }

        Scope scope = ScopeResolver.resolve(type);

        return create(type, scope);
    }

    private <T> T create(Class<T> type, Scope scope) {
        if (creationContext.contains(type)) {
            throw new DependencyException("Circular dependency detected:\n"
                    + creationContext.describe(type));
        }

        creationContext.push(type);

        try {
            Object instance = resolver.create(type);

            if (scope == Scope.SINGLETON) {
                registry.register(type, instance);
            }

            return type.cast(instance);

        } finally {
            creationContext.pop();
        }
    }

    public InstanceRegistry getRegistry() {
        return registry;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}