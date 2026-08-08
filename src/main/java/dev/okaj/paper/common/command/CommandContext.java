package dev.okaj.paper.common.command;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public final class CommandContext {

    private static final Component PLAYER_ONLY = Component.text("This command can only be executed by players.", NamedTextColor.RED);
    private final CommandSourceStack source;
    private final CommandArguments arguments;
    private Component errorMessage;

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

    public String raw() {
        return arguments.raw();
    }

    public CommandSender sender() {
        return source.getSender();
    }

    public Player player() {
        if (!(sender() instanceof Player player)) {
            sender().sendMessage(PLAYER_ONLY);
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

    public Component errorMessage() {
        return errorMessage;
    }

    public void errorMessage(Component message) {
        this.errorMessage = message;
    }
}