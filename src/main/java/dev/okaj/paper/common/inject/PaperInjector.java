package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.BukkitLoggerAdapter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class PaperInjector extends AbstractInjector {

    public final JavaPlugin plugin;

    public PaperInjector(JavaPlugin plugin) {
        super(
                new ServiceRegistry(),
                new ClassScanner(
                        plugin.getClass().getClassLoader(),
                        pluginFile(plugin.getClass()),
                        new BukkitLoggerAdapter(plugin.getLogger())
                ),
                new CreationContext(),
                new BukkitLoggerAdapter(plugin.getLogger())
        );
        this.resolver = new ConstructorResolver(this);
        this.plugin = plugin;

        registry.registerSingleton(JavaPlugin.class, "", plugin);
        registry.registerSingleton(plugin.getClass(), "", plugin);
    }

    private static File pluginFile(Class<?> clazz) {
        try {
            return new File(
                    clazz.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            );
        } catch (Exception e) {
            throw new DependencyException("Could not resolve plugin source", e);
        }
    }

    public @NotNull JavaPlugin plugin() {
        return plugin;
    }
}