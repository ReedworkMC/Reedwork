package dev.okaj.paper.common.command.paper;

import dev.okaj.paper.common.PaperLogger;
import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.CommandRegistry;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

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
