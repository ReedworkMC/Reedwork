package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.BukkitLoggerAdapter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class PaperInjector extends AbstractInjector {

    public final JavaPlugin plugin;

    public PaperInjector(ServiceRegistry registry, ClassScanner classScanner, CreationContext creationContext, BukkitLoggerAdapter logger, JavaPlugin plugin) {
        super(registry, classScanner, creationContext, logger);
        this.plugin = plugin;


    }

    public @NotNull JavaPlugin plugin() {
        return plugin;
    }
}