package dev.okaj.paper.common.command;

import dev.okaj.paper.common.PaperContext;
import dev.okaj.paper.common.command.paper.PaperCommandRegistry;
import dev.okaj.paper.common.module.PaperModule;

public final class CommandModule implements PaperModule {

    @Override
    public void initialize(PaperContext context) {
        PaperCommandRegistry registry = new PaperCommandRegistry(context.plugin());

        CommandProcessor processor = new CommandProcessor(context.injector(), registry);

        context.injector().addProcessor(processor);
    }
}