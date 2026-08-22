package dev.reedworkmc.reedwork.inject.injector;

import dev.reedworkmc.reedwork.logger.BootstrapLoggerAdapter;
import dev.reedworkmc.reedwork.inject.scanner.ClassScanner;
import dev.reedworkmc.reedwork.inject.registry.ServiceRegistry;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class ReedworkBootstrapInjector extends AbstractInjector {

    private final BootstrapContext bootstrap;

    public ReedworkBootstrapInjector(ServiceRegistry registry, ClassScanner classScanner, BootstrapLoggerAdapter logger, BootstrapContext bootstrap) {
        super(registry, classScanner, logger);
        this.bootstrap = bootstrap;
    }

    public BootstrapContext bootstrap() {
        return bootstrap;
    }
}