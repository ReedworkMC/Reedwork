package dev.okaj.paper.common.listener;

import dev.okaj.paper.common.PaperContext;
import dev.okaj.paper.common.module.PaperModule;

public final class ListenerModule implements PaperModule {

    @Override
    public void initialize(PaperContext context) {
        ListenerProcessor processor = new ListenerProcessor(
                context.injector(),
                context.plugin().getServer().getPluginManager()
        );

        context.injector().addProcessor(processor);
    }
}