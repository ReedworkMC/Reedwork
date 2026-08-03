package dev.okaj.paper.common.listener;

import dev.okaj.paper.common.annotation.EventListener;
import dev.okaj.paper.common.inject.ClassProcessor;
import dev.okaj.paper.common.inject.PaperInjector;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public final class ListenerProcessor implements ClassProcessor {

    private final PaperInjector injector;
    private final PluginManager pluginManager;

    public ListenerProcessor(PaperInjector injector, PluginManager pluginManager) {
        this.injector = injector;
        this.pluginManager = pluginManager;
    }

    public void process(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if (!clazz.isAnnotationPresent(EventListener.class)) {
                continue;
            }

            if (!org.bukkit.event.Listener.class.isAssignableFrom(clazz)) {
                throw new IllegalStateException(clazz.getName() + " is annotated with @Listener but does not implement Bukkit Listener");
            }

            org.bukkit.event.Listener listener = (org.bukkit.event.Listener) injector.get(clazz);

            pluginManager.registerEvents(listener, injector.getPlugin());
        }
    }
}