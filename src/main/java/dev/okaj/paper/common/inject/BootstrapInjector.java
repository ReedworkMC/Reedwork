package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.BootstrapLoggerAdapter;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import org.jetbrains.annotations.ApiStatus;

import java.io.File;

@ApiStatus.Experimental
public final class BootstrapInjector extends AbstractInjector {

    public BootstrapInjector(BootstrapContext context) {
        super(
                new ServiceRegistry(),
                new ClassScanner(
                        context.getPluginSource().getClass().getClassLoader(),
                        new File(context.getPluginSource().toUri()),
                        new BootstrapLoggerAdapter(context.getLogger())
                ),
                new CreationContext(),
                new BootstrapLoggerAdapter(context.getLogger())
        );
        this.resolver = new ConstructorResolver(this);

        registry.registerSingleton(BootstrapContext.class, "", context);
    }
}