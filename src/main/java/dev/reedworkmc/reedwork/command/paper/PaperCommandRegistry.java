package dev.reedworkmc.reedwork.command.paper;

import dev.reedworkmc.reedwork.logger.PaperLogger;
import dev.reedworkmc.reedwork.command.CommandDefinition;
import dev.reedworkmc.reedwork.command.CommandRegistry;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;

import java.util.Arrays;

public final class PaperCommandRegistry implements CommandRegistry {

    private final PaperLogger logger;
    private final LifecycleEventManager<?>  lifecycleManager;
    private final PaperCommandBuilder builder;

    public PaperCommandRegistry(PaperLogger logger, LifecycleEventManager<?> lifecycleManager, PaperCommandBuilder builder) {
        this.logger = logger;
        this.lifecycleManager = lifecycleManager;
        this.builder = builder;
    }

    @Override
    public void register(CommandDefinition definition) {
        logger.info("Registering command: " + definition.name());

        lifecycleManager
                .registerEventHandler(
                        LifecycleEvents.COMMANDS,
                        event -> {

                            event.registrar()
                                    .register(
                                            builder.build(definition),
                                            definition.description(),
                                            Arrays.asList(definition.aliases())
                                    );
                        }
                );
    }
}
