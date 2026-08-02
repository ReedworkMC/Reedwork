package dev.okaj.paper.common.command.parameter;

import com.mojang.brigadier.arguments.ArgumentType;

import java.lang.reflect.Parameter;

public final class ParameterDefinition {

    private final Parameter parameter;

    private final ArgumentType<?> argumentType;

    public ParameterDefinition(Parameter parameter, ArgumentType<?> argumentType) {
        this.parameter = parameter;
        this.argumentType = argumentType;
    }

    public Parameter parameter() {
        return parameter;
    }

    public ArgumentType<?> argumentType() {
        return argumentType;
    }

    public String name() {
        return parameter.getName();
    }

    public Class<?> type() {
        return parameter.getType();
    }
}
