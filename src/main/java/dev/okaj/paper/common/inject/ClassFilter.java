package dev.okaj.paper.common.inject;

import java.lang.reflect.Modifier;

public final class ClassFilter {

    private ClassFilter() {
    }

    public static boolean isInjectable(Class<?> clazz) {

        if (clazz.isInterface()) {
            return false;
        }

        if (clazz.isAnnotationPresent(Deprecated.class)) {
            return false;
        }

        if (Modifier.isAbstract(clazz.getModifiers())) {
            return false;
        }

        return true;
    }
}