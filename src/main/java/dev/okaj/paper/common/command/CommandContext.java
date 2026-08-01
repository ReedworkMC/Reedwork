package dev.okaj.paper.common.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandContext {

    private final CommandSender sender;

    public CommandContext(CommandSender sender) {
        this.sender = sender;
    }

    public CommandSender sender() {
        return sender;
    }

    public Player player() {
        if (!(sender instanceof Player player)) {
            throw new IllegalStateException("Only players can execute this command");
        }

        return player;
    }
}