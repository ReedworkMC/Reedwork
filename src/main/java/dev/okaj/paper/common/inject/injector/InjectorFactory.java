package dev.okaj.paper.common.inject.injector;

import dev.okaj.paper.common.inject.provider.SingletonProvider;
import dev.okaj.paper.common.inject.registry.ServiceKey;
import dev.okaj.paper.common.logger.BootstrapLoggerAdapter;
import dev.okaj.paper.common.logger.BukkitLoggerAdapter;
import dev.okaj.paper.common.inject.scanner.ClassScanner;
import dev.okaj.paper.common.inject.exception.DependencyException;
import dev.okaj.paper.common.inject.registry.ServiceRegistry;
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
                logger,
                plugin
        );

        injector.registry.register(new ServiceKey(JavaPlugin.class, ""), new SingletonProvider<>(plugin));
        injector.registry.register(new ServiceKey(plugin.getClass(), ""), new SingletonProvider<>(plugin));

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
