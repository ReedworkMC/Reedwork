package dev.okaj.paper.common.command.module;

import dev.okaj.paper.common.app.context.ApplicationContext;
import dev.okaj.paper.common.command.CommandInvoker;
import dev.okaj.paper.common.command.CommandProcessor;
import dev.okaj.paper.common.command.node.CommandNodeRegistry;
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

        CommandNodeRegistry nodeRegistry = new CommandNodeRegistry();

        CommandInvoker invoker = new CommandInvoker(parameters, nodeRegistry);
        PaperCommandExecutor executor = new PaperCommandExecutor(invoker);
        PaperCommandBuilder builder = new PaperCommandBuilder(executor);
        PaperCommandRegistry registry = new PaperCommandRegistry(context.logger(), context.lifecycleManager(), builder);

        context.injector().addProcessor(
                new CommandProcessor(
                        context.injector(),
                        registry,
                        parameters,
                        nodeRegistry
                )
        );
    }
}