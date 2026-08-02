package dev.okaj.paper.common.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class CommandContext {

    private final CommandSourceStack source;

    public CommandContext(CommandSourceStack source) {
        this.source = source;
    }

    public CommandSender sender() {
        return source.getSender();
    }

    public CommandSourceStack source() {
        return source;
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