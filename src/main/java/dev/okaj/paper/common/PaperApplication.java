package dev.okaj.paper.common;

import dev.okaj.paper.common.command.CommandModule;
import dev.okaj.paper.common.inject.PaperInjector;
import dev.okaj.paper.common.listener.ListenerModule;
import dev.okaj.paper.common.module.ModuleManager;
import dev.okaj.paper.common.module.PaperModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperApplication {

    private final JavaPlugin plugin;
    private final PaperInjector injector;
    private final ModuleManager moduleManager;

    public PaperApplication(JavaPlugin plugin) {
        this.plugin = plugin;
        this.injector = new PaperInjector(plugin);
        this.moduleManager = new ModuleManager();

        installDefaults();
    }

    private void installDefaults() {
        moduleManager.install(new ListenerModule());
        moduleManager.install(new CommandModule());
    }

    public PaperApplication scan(String packageName) {
        PaperContext context = new PaperContext(plugin, injector, moduleManager);
        moduleManager.initialize(context);
        injector.scan(packageName);
        return this;
    }

    public PaperApplication register(PaperModule module) {
        moduleManager.register(module);
        return this;
    }
}