package dev.okaj.paper.common.command;

import dev.okaj.paper.common.PaperContext;
import dev.okaj.paper.common.command.paper.PaperCommandRegistry;
import dev.okaj.paper.common.module.PaperModule;

public final class CommandModule implements PaperModule {

    @Override
    public void initialize(PaperContext context) {
        context.injector().addProcessor(
                new CommandProcessor(
                        context.injector(),
                        new PaperCommandRegistry(context.plugin())
                )
        );
    }
}