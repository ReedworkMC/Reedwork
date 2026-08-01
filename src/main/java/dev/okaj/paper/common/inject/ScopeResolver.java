package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.annotation.Singleton;
import dev.okaj.paper.common.annotation.Transient;

public final class ScopeResolver {

    private ScopeResolver() {
    }

    public static Scope resolve(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Singleton.class)) {
            return Scope.SINGLETON;
        }

        if (clazz.isAnnotationPresent(Transient.class)) {
            return Scope.TRANSIENT;
        }

        throw new DependencyException("Class " + clazz.getName() + " has no scope annotation");
    }
}