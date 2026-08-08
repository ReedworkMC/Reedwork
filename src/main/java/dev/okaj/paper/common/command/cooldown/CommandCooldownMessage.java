package dev.okaj.paper.common.command.cooldown;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

public final class CommandCooldownMessage {

    private static final String MESSAGE = "<red>You are still on cooldown. Please wait <seconds> seconds.";

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public static Component create(long remainingMilliseconds) {
        long remainingSeconds = (long) Math.ceil(remainingMilliseconds / 1000.0);

        return MINI_MESSAGE.deserialize(
                MESSAGE,
                Placeholder.unparsed("seconds", String.valueOf(remainingSeconds))
        );
    }
}