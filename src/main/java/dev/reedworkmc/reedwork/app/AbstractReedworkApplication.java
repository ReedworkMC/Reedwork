package dev.reedworkmc.reedwork.app;

import dev.reedworkmc.reedwork.app.context.ApplicationContext;
import dev.reedworkmc.reedwork.inject.injector.AbstractInjector;
import dev.reedworkmc.reedwork.inject.registry.ServiceRegistry;
import dev.reedworkmc.reedwork.module.ModuleManager;

public abstract class AbstractReedworkApplication {

    protected final AbstractInjector injector;
    protected final ModuleManager moduleManager;
    protected final ApplicationContext context;

    protected AbstractReedworkApplication(AbstractInjector injector, ModuleManager moduleManager, ApplicationContext context) {
        this.injector = injector;
        this.moduleManager = moduleManager;
        this.context = context;
    }

    public ApplicationContext context() {
        return context;
    }

    public AbstractReedworkApplication scan(String packageName) {
        injector.scan(packageName);
        return this;
    }

    public <T> Binder<T> bind(Class<T> type) {
        return new Binder<>(this, type);
    }

    ServiceRegistry registry() {
        return injector.getRegistry();
    }

    AbstractInjector injector() {
        return injector;
    }

    ModuleManager modules() {
        return moduleManager;
    }
}