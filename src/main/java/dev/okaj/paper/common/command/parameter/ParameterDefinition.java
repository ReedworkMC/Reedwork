package dev.okaj.paper.common.command.parameter;

import java.lang.reflect.Parameter;

public final class ParameterDefinition {

    private final Parameter parameter;


    public ParameterDefinition(Parameter parameter) {
        this.parameter = parameter;
    }

    public Parameter parameter() {
        return parameter;
    }

    public String name() {
        return parameter.getName();
    }

    public Class<?> type() {
        return parameter.getType();
    }
}
