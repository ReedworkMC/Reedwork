package dev.okaj.paper.common.command;

import dev.okaj.paper.common.annotation.Command;

public final class CommandMetadata {

    private String name;
    private String description;
    private String permission;
    private String usage;
    private String[] aliases;

    static CommandMetadata of(Command cmd) {
        CommandMetadata meta = new CommandMetadata();
        meta.name = cmd.value();
        meta.description = cmd.description();
        meta.permission = cmd.permission();
        meta.usage = cmd.usage();
        meta.aliases = cmd.aliases();
        return meta;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public String permission() {
        return permission;
    }

    public String usage() {
        return usage;
    }

    public String[] aliases() {
        return aliases;
    }
}
