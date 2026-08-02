package dev.okaj.paper.common.command.paper;

import dev.okaj.paper.common.command.CommandDefinition;
import dev.okaj.paper.common.command.CommandRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperCommandRegistry implements CommandRegistry {

    private final JavaPlugin plugin;

    public PaperCommandRegistry(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void register(CommandDefinition definition) {
        String name = definition.name();

        plugin.getLogger().info("Registering command: " + name);
        //todo :=)
    }
}
