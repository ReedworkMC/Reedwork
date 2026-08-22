package dev.reedworkmc.reedwork.app.context;

import dev.reedworkmc.reedwork.inject.injector.AbstractInjector;
import dev.reedworkmc.reedwork.inject.injector.ReedworkBootstrapInjector;
import dev.reedworkmc.reedwork.logger.BootstrapLoggerAdapter;
import dev.reedworkmc.reedwork.logger.PaperLogger;
import dev.reedworkmc.reedwork.module.ModuleManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class BootstrapReedworkContext implements ApplicationContext {

    private final BootstrapContext context;
    private final ReedworkBootstrapInjector injector;
    private final ModuleManager modules;
    private final PaperLogger logger;

    public BootstrapReedworkContext(BootstrapContext context, ReedworkBootstrapInjector injector, ModuleManager modules) {
        this.context = context;
        this.injector = injector;
        this.modules = modules;
        this.logger = new BootstrapLoggerAdapter(context.getLogger());
    }

    public BootstrapContext bootstrap() {
        return context;
    }

    @Override
    public AbstractInjector injector() {
        return injector;
    }

    @Override
    public ModuleManager modules() {
        return modules;
    }

    @Override
    public PaperLogger logger() {
        return logger;
    }

    @Override
    public LifecycleEventManager<BootstrapContext> lifecycleManager() {
        return context.getLifecycleManager();
    }
}