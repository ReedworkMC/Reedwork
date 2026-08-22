package dev.reedworkmc.reedwork.inject.injector;

import dev.reedworkmc.reedwork.logger.BukkitLoggerAdapter;
import dev.reedworkmc.reedwork.inject.scanner.ClassScanner;
import dev.reedworkmc.reedwork.inject.registry.ServiceRegistry;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class ReedworkInjector extends AbstractInjector {

    public final JavaPlugin plugin;

    public ReedworkInjector(ServiceRegistry registry, ClassScanner classScanner, BukkitLoggerAdapter logger, JavaPlugin plugin) {
        super(registry, classScanner, logger);
        this.plugin = plugin;
    }

    public @NotNull JavaPlugin plugin() {
        return plugin;
    }
}