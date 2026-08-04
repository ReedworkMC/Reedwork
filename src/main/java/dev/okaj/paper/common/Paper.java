package dev.okaj.paper.common;

import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.bukkit.plugin.java.JavaPlugin;

public final class Paper {

    //todo paper bootstrap for Commands and Enchantments
    //todo enchantments

    public static PaperApplication create(JavaPlugin plugin) {
        return PaperApplication.create(plugin);
    }

    public static PaperBootstrapApplication bootstrap(BootstrapContext bootstrap) {
        return PaperBootstrapApplication.create(bootstrap);
    }
}