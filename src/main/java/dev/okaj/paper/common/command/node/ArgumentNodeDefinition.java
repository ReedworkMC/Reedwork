package dev.okaj.paper.common.command.node;

import com.mojang.brigadier.arguments.ArgumentType;

public class ArgumentNodeDefinition extends CommandNodeDefinition{

    private final ArgumentType<?> type;

    public ArgumentNodeDefinition(String name, ArgumentType<?> type) {
        super(name);
        this.type = type;
    }

    public ArgumentType<?> type() {
        return type;
    }
}
