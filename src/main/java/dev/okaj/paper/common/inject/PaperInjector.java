package dev.okaj.paper.common.inject;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.logging.Level;

public final class PaperInjector {

    private final JavaPlugin plugin;
    private final InstanceRegistry registry;
    private final ClassScanner scanner;

    public PaperInjector(JavaPlugin plugin) {
        this.plugin = plugin;
        this.registry = new InstanceRegistry();
        this.scanner = new ClassScanner(plugin);


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
            plugin.getLogger().log(Level.INFO,"Found: " + clazz.getName());
        }
    }

    public InstanceRegistry getRegistry() {
        return registry;
    }
}