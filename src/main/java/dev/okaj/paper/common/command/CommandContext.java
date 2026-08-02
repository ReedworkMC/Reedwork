package dev.okaj.paper.common.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class CommandContext {

    private final CommandSourceStack source;
    private final CommandArguments arguments;

    public CommandContext(CommandSourceStack source, CommandArguments arguments) {
        this.source = source;
        this.arguments = arguments;
    }

    public CommandSourceStack source() {
        return source;
    }

    public CommandArguments arguments() {
        return arguments;
    }

    public CommandSender sender() {
        return source.getSender();
    }

    public Player player() {
        if (!(sender() instanceof Player player)) {
            throw new CommandException("Only players can execute this command");
        }

        return player;
    }

    public Entity entity() {
        return source.getExecutor();
    }

    public boolean isPlayer() {
        return sender() instanceof Player;
    }
}