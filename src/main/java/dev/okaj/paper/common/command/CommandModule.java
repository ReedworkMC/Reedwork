package dev.okaj.paper.common.command;

import dev.okaj.paper.common.ApplicationContext;
import dev.okaj.paper.common.PaperContext;
import dev.okaj.paper.common.command.paper.PaperCommandBuilder;
import dev.okaj.paper.common.command.paper.PaperCommandExecutor;
import dev.okaj.paper.common.command.paper.PaperCommandRegistry;
import dev.okaj.paper.common.command.parameter.DefaultParameterResolvers;
import dev.okaj.paper.common.command.parameter.ParameterResolverRegistry;
import dev.okaj.paper.common.module.PaperModule;

public final class CommandModule implements PaperModule {

    @Override
    public void initialize(ApplicationContext context) {

        ParameterResolverRegistry parameters = new ParameterResolverRegistry();
        DefaultParameterResolvers.register(parameters);

        CommandInvoker invoker = new CommandInvoker(parameters);
        PaperCommandExecutor executor = new PaperCommandExecutor(invoker);
        PaperCommandBuilder builder = new PaperCommandBuilder(executor);
        PaperCommandRegistry registry = new PaperCommandRegistry(context.logger(), context.lifecycleManager(), builder); //fixme

        context.injector().addProcessor(
                new CommandProcessor(
                        context.injector(),
                        registry,
                        parameters
                )
        );
    }
}