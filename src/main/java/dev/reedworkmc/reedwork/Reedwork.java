package dev.reedworkmc.reedwork;

import dev.reedworkmc.reedwork.app.ReedworkApplication;
import dev.reedworkmc.reedwork.app.ReedworkBootstrapApplication;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.bukkit.plugin.java.JavaPlugin;

public final class Reedwork {

    public static ReedworkApplication create(JavaPlugin plugin) {
        return ReedworkApplication.create(plugin);
    }

    public static ReedworkBootstrapApplication bootstrap(BootstrapContext bootstrap) {
        return ReedworkBootstrapApplication.create(bootstrap);
    }
}