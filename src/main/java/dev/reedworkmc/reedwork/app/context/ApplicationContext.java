package dev.reedworkmc.reedwork.app.context;

import dev.reedworkmc.reedwork.inject.injector.AbstractInjector;
import dev.reedworkmc.reedwork.logger.PaperLogger;
import dev.reedworkmc.reedwork.module.ModuleManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;

public interface ApplicationContext {

    AbstractInjector injector();

    ModuleManager modules();

    PaperLogger logger();

    LifecycleEventManager<? extends LifecycleEventOwner> lifecycleManager();
}
