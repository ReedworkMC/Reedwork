package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.annotation.Singleton;
import dev.okaj.paper.common.annotation.Transient;

import java.util.List;

public final class AnnotationProcessor implements ClassProcessor {

    private final PaperInjector injector;

    public AnnotationProcessor(PaperInjector injector) {
        this.injector = injector;
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
    }
}