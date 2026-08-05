package dev.okaj.paper.common.listener.module;

import dev.okaj.paper.common.app.context.ApplicationContext;
import dev.okaj.paper.common.app.context.PaperContext;
import dev.okaj.paper.common.inject.injector.PaperInjector;
import dev.okaj.paper.common.listener.ListenerProcessor;
import dev.okaj.paper.common.module.PaperModule;

public final class ListenerModule implements PaperModule {

    @Override
    public void initialize(ApplicationContext context) {
        ListenerProcessor processor = new ListenerProcessor(
                (PaperInjector) context.injector(),
                ((PaperContext) context).plugin().getServer().getPluginManager()
        );

        context.injector().addProcessor(processor);
    }
}