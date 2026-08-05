package dev.okaj.paper.common.inject.injector;

import dev.okaj.paper.common.logger.BootstrapLoggerAdapter;
import dev.okaj.paper.common.inject.scanner.ClassScanner;
import dev.okaj.paper.common.inject.registry.ServiceRegistry;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
public final class BootstrapInjector extends AbstractInjector {

    private final BootstrapContext bootstrap;

    public BootstrapInjector(ServiceRegistry registry, ClassScanner classScanner, BootstrapLoggerAdapter logger, BootstrapContext bootstrap) {
        super(registry, classScanner, logger);
        this.bootstrap = bootstrap;
    }

    public BootstrapContext bootstrap() {
        return bootstrap;
    }
}