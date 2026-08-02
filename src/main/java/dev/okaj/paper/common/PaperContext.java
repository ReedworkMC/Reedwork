package dev.okaj.paper.common;

import dev.okaj.paper.common.inject.PaperInjector;
import dev.okaj.paper.common.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperContext {

    private final JavaPlugin plugin;
    private final PaperInjector injector;
    private final ModuleManager moduleManager;

    public PaperContext(JavaPlugin plugin, PaperInjector injector, ModuleManager moduleManager) {
        this.plugin = plugin;
        this.injector = injector;
        this.moduleManager = moduleManager;
    }

    public JavaPlugin plugin() {
        return plugin;
    }

    public PaperInjector injector() {
        return injector;
    }

    public ModuleManager modules() {
        return moduleManager;
    }

    public java.util.logging.Logger logger() {
        return plugin.getLogger();
    }
}