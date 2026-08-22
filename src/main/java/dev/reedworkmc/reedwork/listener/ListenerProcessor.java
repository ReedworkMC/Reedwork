package dev.reedworkmc.reedwork.listener;

import dev.reedworkmc.reedwork.annotation.EventListener;
import dev.reedworkmc.reedwork.inject.processor.ClassProcessor;
import dev.reedworkmc.reedwork.inject.injector.ReedworkInjector;
import org.bukkit.plugin.PluginManager;

import java.util.List;

public final class ListenerProcessor implements ClassProcessor {

    private final ReedworkInjector injector;
    private final PluginManager pluginManager;

    public ListenerProcessor(ReedworkInjector injector, PluginManager pluginManager) {
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

            pluginManager.registerEvents(listener, injector.plugin());
        }
    }
}