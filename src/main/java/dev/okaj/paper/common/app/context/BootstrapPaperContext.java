package dev.okaj.paper.common.app.context;

import dev.okaj.paper.common.inject.injector.AbstractInjector;
import dev.okaj.paper.common.inject.injector.BootstrapInjector;
import dev.okaj.paper.common.logger.BootstrapLoggerAdapter;
import dev.okaj.paper.common.logger.PaperLogger;
import dev.okaj.paper.common.module.ModuleManager;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class BootstrapPaperContext implements ApplicationContext {

    private final BootstrapContext context;
    private final BootstrapInjector injector;
    private final ModuleManager modules;
    private final PaperLogger logger;

    public BootstrapPaperContext(BootstrapContext context, BootstrapInjector injector, ModuleManager modules) {
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