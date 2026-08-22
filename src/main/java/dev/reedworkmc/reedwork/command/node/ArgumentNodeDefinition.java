package dev.reedworkmc.reedwork.command.node;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.reedworkmc.reedwork.command.parameter.ParameterSuggestion;

public class ArgumentNodeDefinition extends CommandNodeDefinition {

    private final ArgumentType<?> argumentType;
    private final ParameterSuggestion suggestion;

    public ArgumentNodeDefinition(String name, ArgumentType<?> argumentType, ParameterSuggestion suggestion) {
        super(name);
        this.argumentType = argumentType;
        this.suggestion = suggestion;
    }

    public ArgumentType<?> argumentType() {
        return argumentType;
    }

    public ParameterSuggestion suggestion() {
        return suggestion;
    }

    public boolean hasSuggestion() {
        return suggestion != null;
    }
}
