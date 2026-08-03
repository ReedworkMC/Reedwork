package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.Command;
import dev.okaj.paper.common.command.parameter.ParameterResolverRegistry;
import dev.okaj.paper.common.inject.*;

import java.util.List;

public final class CommandProcessor implements ClassProcessor {

    private final PaperInjector injector;
    private final CommandScanner scanner;
    private final CommandRegistry registry;


    public CommandProcessor(PaperInjector injector, CommandRegistry registry, ParameterResolverRegistry parameters) {
        this.injector = injector;
        this.registry = registry;

        this.scanner = new CommandScanner(new CommandMethodParser(parameters));
    }

    public void process(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            Command annotation = clazz.getAnnotation(Command.class);

            if (annotation == null) {
                continue;
            }

            Object instance = injector.get(clazz);
            CommandDefinition definition = scanner.scan(instance);

            registry.register(definition);
        }
    }
}