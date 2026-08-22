package dev.reedworkmc.reedwork.command;

import dev.reedworkmc.reedwork.annotation.Command;
import dev.reedworkmc.reedwork.command.parameter.ParameterResolverRegistry;
import dev.reedworkmc.reedwork.inject.injector.InjectorDependencyProvider;
import dev.reedworkmc.reedwork.inject.processor.ClassProcessor;
import dev.reedworkmc.reedwork.logger.PaperLogger;

import java.util.List;

public final class CommandProcessor implements ClassProcessor {

    private final InjectorDependencyProvider injector;
    private final CommandScanner scanner;
    private final CommandRegistry registry;
    private final PaperLogger logger;

    public CommandProcessor(InjectorDependencyProvider injector, CommandRegistry registry, ParameterResolverRegistry parameters, PaperLogger logger) {
        this.injector = injector;
        this.registry = registry;
        this.logger = logger;

        this.scanner = new CommandScanner(new CommandMethodParser(parameters));
    }

    public void process(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            Command annotation = clazz.getAnnotation(Command.class);

            if (annotation == null) {
                continue;
            }

            Object instance = injector.get(clazz);

            try {
                CommandDefinition definition = scanner.scan(instance);
                registry.register(definition);

            } catch (Exception e) {
                logger.error("Failed validating Command: " + clazz.getName(), e);
            }
        }
    }
}