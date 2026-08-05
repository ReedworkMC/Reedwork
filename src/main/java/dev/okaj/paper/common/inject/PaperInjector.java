package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.BukkitLoggerAdapter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class PaperInjector extends AbstractInjector {

    public final JavaPlugin plugin;

    public PaperInjector(ServiceRegistry registry, ClassScanner classScanner, BukkitLoggerAdapter logger, JavaPlugin plugin) {
        super(registry, classScanner, logger);
        this.plugin = plugin;
    }

    public @NotNull JavaPlugin plugin() {
        return plugin;
    }
}