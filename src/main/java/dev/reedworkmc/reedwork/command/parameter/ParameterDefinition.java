package dev.reedworkmc.reedwork.command.parameter;

import java.lang.reflect.Parameter;

public final class ParameterDefinition {

    private final Parameter parameter;
    private final int index;
    private final int totalArguments;

    public ParameterDefinition(Parameter parameter, int index, int totalArguments) {
        this.parameter = parameter;
        this.index = index;
        this.totalArguments = totalArguments;
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

    public boolean isLastArgument() {
        return index == totalArguments - 1;
    }
}
