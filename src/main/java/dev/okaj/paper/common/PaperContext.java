package dev.okaj.paper.common;

import dev.okaj.paper.common.inject.injector.AbstractInjector;
import dev.okaj.paper.common.inject.injector.PaperInjector;
import dev.okaj.paper.common.module.ModuleManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperContext implements ApplicationContext {

    private final PaperInjector injector;
    private final ModuleManager moduleManager;
    private final PaperLogger logger;

    public PaperContext(PaperInjector injector, ModuleManager moduleManager) {
        this.injector = injector;
        this.moduleManager = moduleManager;
        this.logger = new BukkitLoggerAdapter(injector.plugin().getLogger());
    }

    public JavaPlugin plugin() {
        return injector.plugin();
    }

    @Override
    public AbstractInjector injector() {
        return injector;
    }

    @Override
    public ModuleManager modules() {
        return moduleManager;
    }

    @Override
    public PaperLogger logger() {
        return logger;
    }

    @Override
    public LifecycleEventManager<? extends LifecycleEventOwner> lifecycleManager() {
        return plugin().getLifecycleManager();
    }
}