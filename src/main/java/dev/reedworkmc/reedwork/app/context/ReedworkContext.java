package dev.reedworkmc.reedwork.app.context;

import dev.reedworkmc.reedwork.inject.injector.AbstractInjector;
import dev.reedworkmc.reedwork.inject.injector.ReedworkInjector;
import dev.reedworkmc.reedwork.logger.BukkitLoggerAdapter;
import dev.reedworkmc.reedwork.logger.PaperLogger;
import dev.reedworkmc.reedwork.module.ModuleManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import org.bukkit.plugin.java.JavaPlugin;

public final class ReedworkContext implements ApplicationContext {

    private final ReedworkInjector injector;
    private final ModuleManager moduleManager;
    private final PaperLogger logger;

    public ReedworkContext(ReedworkInjector injector, ModuleManager moduleManager) {
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