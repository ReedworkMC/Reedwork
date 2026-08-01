package dev.okaj.paper.common.inject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public final class ConstructorResolver {

    private final PaperInjector injector;

    public ConstructorResolver(PaperInjector injector) {
        this.injector = injector;
    }

    public Object create(Class<?> clazz) {
        try {
            Constructor<?> constructor = findConstructor(clazz);

            Object[] parameters =
                    Arrays.stream(constructor.getParameterTypes())
                            .map(this::resolveDependency)
                            .toArray();

            constructor.setAccessible(true);

            return constructor.newInstance(parameters);

        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new DependencyException("Could not create " + clazz.getName(), e);
        }
    }


    private Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();

        if (constructors.length == 1) {
            return constructors[0];
        }

        return Arrays.stream(constructors)
                .filter(c ->
                        c.getParameterCount() == 0
                )
                .findFirst()
                .orElseThrow(() ->
                        new DependencyException("No usable constructor found for " + clazz.getName())
                );
    }


    private Object resolveDependency(Class<?> type) {
        return injector.get(type);
    }
}