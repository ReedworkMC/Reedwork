package dev.okaj.paper.common;

import org.bukkit.plugin.java.JavaPlugin;

public final class Paper {

    private Paper() {
    }

    public static PaperApplication create(JavaPlugin plugin) {
        return new PaperApplication(plugin);
    }
}