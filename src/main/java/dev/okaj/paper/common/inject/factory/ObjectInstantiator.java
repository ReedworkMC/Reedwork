package dev.okaj.paper.common.inject.factory;

import dev.okaj.paper.common.inject.exception.DependencyException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class ObjectInstantiator {

    public Object instantiate(Constructor<?> constructor, Object[] parameters) {
        try {
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);

        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException e) {

            throw new DependencyException(
                    "Could not instantiate "
                            + constructor.getDeclaringClass().getName(),
                    e
            );
        }
    }
}