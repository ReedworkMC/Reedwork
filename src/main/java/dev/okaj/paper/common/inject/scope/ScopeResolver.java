package dev.okaj.paper.common.inject.scope;

import dev.okaj.paper.common.annotation.Singleton;
import dev.okaj.paper.common.annotation.Transient;
import dev.okaj.paper.common.inject.DependencyException;

import java.lang.annotation.Annotation;

public final class ScopeResolver {

    public static Scope resolve(Class<?> clazz) {
        if (hasAnnotation(clazz, Singleton.class)) {
            return Scope.SINGLETON;
        }

        if (hasAnnotation(clazz, Transient.class)) {
            return Scope.TRANSIENT;
        }

        throw new DependencyException("No DI registration for " + clazz.getName());
    }

    public static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> target) {
        if (clazz.isAnnotationPresent(target)) {
            return true;
        }

        for (Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().isAnnotationPresent(target)) {
                return true;
            }
        }

        return false;
    }
}