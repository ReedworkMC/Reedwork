package dev.okaj.paper.common;

import dev.okaj.paper.common.inject.PaperInjector;
import dev.okaj.paper.common.inject.ServiceRegistry;
import dev.okaj.paper.common.module.ModuleManager;

public abstract class AbstractPaperApplication {

    protected final PaperInjector injector;
    protected final ModuleManager moduleManager;

    protected AbstractPaperApplication(PaperInjector injector) {
        this.injector = injector;
        this.moduleManager = new ModuleManager();
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

    PaperInjector injector() {
        return injector;
    }

    ModuleManager modules() {
        return moduleManager;
    }
}