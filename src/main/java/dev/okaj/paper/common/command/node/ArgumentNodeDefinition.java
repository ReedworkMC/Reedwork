package dev.okaj.paper.common.command.node;

import com.mojang.brigadier.arguments.ArgumentType;

public class ArgumentNodeDefinition extends CommandNodeDefinition{

    private final ArgumentType<?> argumentType;

    public ArgumentNodeDefinition(String name, ArgumentType<?> argumentType) {
        super(name);
        this.argumentType = argumentType;
    }

    public ArgumentType<?> argumentType() {
        return argumentType;
    }
}
