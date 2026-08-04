package dev.okaj.paper.common;

import dev.okaj.paper.common.command.CommandModule;
import dev.okaj.paper.common.inject.PaperInjector;
import dev.okaj.paper.common.inject.ServiceRegistry;
import dev.okaj.paper.common.listener.ListenerModule;
import org.bukkit.plugin.java.JavaPlugin;

public final class PaperApplication extends AbstractPaperApplication {

    public PaperApplication(JavaPlugin plugin) {
        super(new PaperInjector(plugin));

        PaperContext paperContext = new PaperContext(plugin, injector, moduleManager);

        installDefaults();

        moduleManager.initialize(paperContext);
    }

    private void installDefaults() {
        moduleManager.install(new ListenerModule());
        moduleManager.install(new CommandModule());
    }
}