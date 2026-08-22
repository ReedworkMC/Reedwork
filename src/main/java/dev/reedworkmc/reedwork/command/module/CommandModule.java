package dev.reedworkmc.reedwork.command.module;

import dev.reedworkmc.reedwork.app.context.ApplicationContext;
import dev.reedworkmc.reedwork.command.CommandInvoker;
import dev.reedworkmc.reedwork.command.CommandProcessor;
import dev.reedworkmc.reedwork.command.cooldown.CommandCooldownService;
import dev.reedworkmc.reedwork.command.paper.PaperCommandBuilder;
import dev.reedworkmc.reedwork.command.paper.PaperCommandExecutor;
import dev.reedworkmc.reedwork.command.paper.PaperCommandRegistry;
import dev.reedworkmc.reedwork.command.parameter.DefaultParameterResolvers;
import dev.reedworkmc.reedwork.command.parameter.ParameterResolverRegistry;
import dev.reedworkmc.reedwork.module.ReedworkModule;

public final class CommandModule implements ReedworkModule {

    @Override
    public void initialize(ApplicationContext context) {

        ParameterResolverRegistry parameters = new ParameterResolverRegistry();
        DefaultParameterResolvers.register(parameters);

        CommandCooldownService cooldownService = new CommandCooldownService();

        CommandInvoker invoker = new CommandInvoker(parameters, context.logger());
        PaperCommandExecutor executor = new PaperCommandExecutor(invoker, cooldownService);
        PaperCommandBuilder builder = new PaperCommandBuilder(executor, context.injector());
        PaperCommandRegistry registry = new PaperCommandRegistry(context.logger(), context.lifecycleManager(), builder);

        context.injector().addProcessor(
                new CommandProcessor(
                        context.injector(),
                        registry,
                        parameters,
                        context.logger()
                )
        );
    }
}