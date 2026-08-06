package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.Command;
import dev.okaj.paper.common.command.node.CommandNodeRegistry;
import dev.okaj.paper.common.command.parameter.ParameterResolverRegistry;
import dev.okaj.paper.common.inject.injector.InjectorDependencyProvider;
import dev.okaj.paper.common.inject.processor.ClassProcessor;
import dev.okaj.paper.common.logger.PaperLogger;

import java.util.List;

public final class CommandProcessor implements ClassProcessor {

    private final InjectorDependencyProvider injector;
    private final CommandScanner scanner;
    private final CommandRegistry registry;
    private final PaperLogger logger;

    public CommandProcessor(InjectorDependencyProvider injector, CommandRegistry registry, ParameterResolverRegistry parameters, PaperLogger logger, CommandNodeRegistry nodeRegistry) {
        this.injector = injector;
        this.registry = registry;
        this.logger = logger;

        this.scanner = new CommandScanner(new CommandMethodParser(parameters), nodeRegistry);
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