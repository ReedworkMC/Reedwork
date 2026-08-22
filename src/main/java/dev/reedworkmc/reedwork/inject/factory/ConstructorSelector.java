package dev.reedworkmc.reedwork.inject.factory;

import dev.reedworkmc.reedwork.inject.exception.DependencyException;

import java.lang.reflect.Constructor;
import java.util.Arrays;

public final class ConstructorSelector {

    public Constructor<?> select(Class<?> type) {
        Constructor<?>[] constructors = type.getDeclaredConstructors();

        if (constructors.length == 1) {
            return constructors[0];
        }

        return Arrays.stream(constructors)
                .filter(c -> c.getParameterCount() == 0)
                .findFirst()
                .orElseThrow(() ->
                        new DependencyException(
                                "No usable constructor found for "
                                        + type.getName()
                        )
                );
    }
}