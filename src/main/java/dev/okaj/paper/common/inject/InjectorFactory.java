package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.BootstrapLoggerAdapter;
import dev.okaj.paper.common.BukkitLoggerAdapter;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class InjectorFactory {

    public static PaperInjector create(JavaPlugin plugin) {
        BukkitLoggerAdapter logger = new BukkitLoggerAdapter(plugin.getLogger());
        PaperInjector injector = new PaperInjector(
                new ServiceRegistry(),
                new ClassScanner(
                        plugin.getClass().getClassLoader(),
                        pluginFile(plugin.getClass()),
                        logger
                ),
                new CreationContext(),
                logger,
                plugin
        );

        injector.registry.registerSingleton(JavaPlugin.class, "", plugin);
        injector.registry.registerSingleton(plugin.getClass(), "", plugin);

        return injector;
    }

    public static BootstrapInjector create(BootstrapContext bootstrap) {
        BootstrapLoggerAdapter logger = new BootstrapLoggerAdapter(bootstrap.getLogger());
        BootstrapInjector injector = new BootstrapInjector(
                new ServiceRegistry(),
                new ClassScanner(
                        InjectorFactory.class.getClassLoader(),
                        new File(bootstrap.getPluginSource().toUri()),
                        logger
                ),
                new CreationContext(),
                logger,
                bootstrap
        );

        injector.registry.registerSingleton(BootstrapContext.class, "", bootstrap);

        return injector;
    }

    private static File pluginFile(Class<?> clazz) {
        try {
            return new File(
                    clazz.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
            );
        } catch (Exception e) {
            throw new DependencyException("Could not resolve plugin source", e);
        }
    }
}
