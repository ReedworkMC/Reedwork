package dev.okaj.paper.common;

import dev.okaj.paper.common.command.CommandModule;
import dev.okaj.paper.common.inject.PaperInjector;
import dev.okaj.paper.common.inject.ServiceRegistry;
import dev.okaj.paper.common.listener.ListenerModule;
import dev.okaj.paper.common.module.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperApplication {

    private final PaperInjector injector;
    private final ModuleManager moduleManager;

    public PaperApplication(JavaPlugin plugin) {
        this.injector = new PaperInjector(plugin);
        this.moduleManager = new ModuleManager();
        PaperContext paperContext = new PaperContext(plugin, injector, moduleManager);

        installDefaults();

        moduleManager.initialize(paperContext);
    }

    private void installDefaults() {
        moduleManager.install(new ListenerModule());
        moduleManager.install(new CommandModule());
    }

    public PaperApplication scan(String packageName) {
        injector.scan(packageName);
        return this;
    }

    public <T> Binder<T> bind(Class<T> type) {
        return new Binder<>(this, type);
    }

    ServiceRegistry registry() {
        return injector.getRegistry();
    }

    PaperInjector injector() {
        return injector;
    }
}