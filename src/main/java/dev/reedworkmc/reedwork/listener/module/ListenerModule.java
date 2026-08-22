package dev.reedworkmc.reedwork.listener.module;

import dev.reedworkmc.reedwork.app.context.ApplicationContext;
import dev.reedworkmc.reedwork.app.context.ReedworkContext;
import dev.reedworkmc.reedwork.inject.injector.ReedworkInjector;
import dev.reedworkmc.reedwork.listener.ListenerProcessor;
import dev.reedworkmc.reedwork.module.ReedworkModule;

public final class ListenerModule implements ReedworkModule {

    @Override
    public void initialize(ApplicationContext context) {
        ListenerProcessor processor = new ListenerProcessor(
                (ReedworkInjector) context.injector(),
                ((ReedworkContext) context).plugin().getServer().getPluginManager()
        );

        context.injector().addProcessor(processor);
    }
}