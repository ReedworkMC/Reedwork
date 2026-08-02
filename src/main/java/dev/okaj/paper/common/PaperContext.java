package dev.okaj.paper.common;

import dev.okaj.paper.common.inject.PaperInjector;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperContext {

    private final JavaPlugin plugin;
    private final PaperInjector injector;

    public PaperContext(JavaPlugin plugin, PaperInjector injector) {
        this.plugin = plugin;
        this.injector = injector;
    }

    public JavaPlugin plugin() {
        return plugin;
    }

    public PaperInjector injector() {
        return injector;
    }
}