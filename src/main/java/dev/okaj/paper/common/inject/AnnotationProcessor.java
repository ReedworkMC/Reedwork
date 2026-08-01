package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.annotation.Singleton;
import dev.okaj.paper.common.annotation.Transient;
import dev.okaj.paper.common.listener.ListenerProcessor;

import java.util.List;

public final class AnnotationProcessor {

    private final PaperInjector injector;
    private final ListenerProcessor listenerProcessor;

    public AnnotationProcessor(PaperInjector injector, ListenerProcessor listenerProcessor) {
        this.injector = injector;
        this.listenerProcessor = listenerProcessor;
    }

    public void process(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {

            if (!ClassFilter.isInjectable(clazz)) {
                continue;
            }

            if (clazz.isAnnotationPresent(Singleton.class) || clazz.isAnnotationPresent(Transient.class)) {
                injector.initialize(clazz);
            }
        }

        listenerProcessor.process(classes);
    }
}