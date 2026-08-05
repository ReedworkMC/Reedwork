package dev.okaj.paper.common.inject;

import dev.okaj.paper.common.annotation.Named;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;

public final class ParameterResolver {

    private final DependencyProvider provider;

    public ParameterResolver(DependencyProvider provider) {
        this.provider = provider;
    }

    public Object[] resolve(Constructor<?> constructor) {

        return Arrays.stream(constructor.getParameters())
                .map(this::resolve)
                .toArray();
    }

    private Object resolve(Parameter parameter) {

        Named named = parameter.getAnnotation(Named.class);

        if (named != null) {
            return provider.get(parameter.getType(), named.value());
        }

        return provider.get(parameter.getType());
    }
}