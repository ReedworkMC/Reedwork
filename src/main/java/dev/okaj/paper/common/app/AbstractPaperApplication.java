package dev.okaj.paper.common.app;

import dev.okaj.paper.common.app.context.ApplicationContext;
import dev.okaj.paper.common.inject.injector.AbstractInjector;
import dev.okaj.paper.common.inject.registry.ServiceRegistry;
import dev.okaj.paper.common.module.ModuleManager;

public abstract class AbstractPaperApplication {

    protected final AbstractInjector injector;
    protected final ModuleManager moduleManager;
    protected final ApplicationContext context;

    protected AbstractPaperApplication(AbstractInjector injector, ModuleManager moduleManager, ApplicationContext context) {
        this.injector = injector;
        this.moduleManager = moduleManager;
        this.context = context;
    }

    public ApplicationContext context() {
        return context;
    }

    public AbstractPaperApplication scan(String packageName) {
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