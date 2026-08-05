package dev.okaj.paper.common;

import dev.okaj.paper.common.inject.injector.AbstractInjector;
import dev.okaj.paper.common.module.ModuleManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;

public interface ApplicationContext {

    AbstractInjector injector();

    ModuleManager modules();

    PaperLogger logger();

    LifecycleEventManager<? extends LifecycleEventOwner> lifecycleManager();
}
