package dev.okaj.paper.common.command.paper;

import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.CommandRegistry;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperCommandRegistry implements CommandRegistry {

    private final JavaPlugin plugin;
    private final PaperCommandBuilder builder;

    public PaperCommandRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
        this.builder = new PaperCommandBuilder();
    }

    @Override
    public void register(CommandDefinition definition) {
        plugin.getLogger().info("Registering command: " + definition.name());
        plugin.getLifecycleManager()
                .registerEventHandler(
                        LifecycleEvents.COMMANDS,
                        event -> {

                            Commands commands = event.registrar();

                            commands.register(
                                    builder.build(definition),
                                    definition.description(),
                                    java.util.List.of(definition.aliases())
                            );
                        }
                );
    }
}
