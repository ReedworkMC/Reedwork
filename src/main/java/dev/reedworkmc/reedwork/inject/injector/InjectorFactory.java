package dev.reedworkmc.reedwork.inject.injector;

import dev.reedworkmc.reedwork.inject.provider.SingletonProvider;
import dev.reedworkmc.reedwork.inject.registry.ServiceKey;
import dev.reedworkmc.reedwork.logger.BootstrapLoggerAdapter;
import dev.reedworkmc.reedwork.logger.BukkitLoggerAdapter;
import dev.reedworkmc.reedwork.inject.scanner.ClassScanner;
import dev.reedworkmc.reedwork.inject.exception.DependencyException;
import dev.reedworkmc.reedwork.inject.registry.ServiceRegistry;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class InjectorFactory {

    public static ReedworkInjector create(JavaPlugin plugin) {
        BukkitLoggerAdapter logger = new BukkitLoggerAdapter(plugin.getLogger());
        ReedworkInjector injector = new ReedworkInjector(
                new ServiceRegistry(),
                new ClassScanner(
                        plugin.getClass().getClassLoader(),
                        pluginFile(plugin.getClass()),
                        logger
                ),
                logger,
                plugin
        );

        injector.registry.register(new ServiceKey(JavaPlugin.class, ""), new SingletonProvider<>(plugin));
        injector.registry.register(new ServiceKey(plugin.getClass(), ""), new SingletonProvider<>(plugin));

        return injector;
    }

    public static ReedworkBootstrapInjector create(BootstrapContext bootstrap) {
        BootstrapLoggerAdapter logger = new BootstrapLoggerAdapter(bootstrap.getLogger());
        ReedworkBootstrapInjector injector = new ReedworkBootstrapInjector(
                new ServiceRegistry(),
                new ClassScanner(
                        InjectorFactory.class.getClassLoader(),
                        new File(bootstrap.getPluginSource().toUri()),
                        logger
                ),
                logger,
                bootstrap
        );

        injector.registry.register(new ServiceKey(BootstrapContext.class, ""), new SingletonProvider<>(bootstrap));

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
