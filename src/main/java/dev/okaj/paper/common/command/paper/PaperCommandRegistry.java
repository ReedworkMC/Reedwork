package dev.okaj.paper.common.command.paper;

import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.CommandRegistry;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public final class PaperCommandRegistry implements CommandRegistry {

    private final JavaPlugin plugin;
    private final PaperCommandBuilder builder;

    public PaperCommandRegistry(JavaPlugin plugin, PaperCommandBuilder builder) {
        this.plugin = plugin;
        this.builder = builder;
    }

    @Override
    public void register(CommandDefinition definition) {
        plugin.getLogger().info("Registering command: " + definition.name());

        plugin.getLifecycleManager()
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
