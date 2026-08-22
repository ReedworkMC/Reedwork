package dev.reedworkmc.reedwork.command.debug;

import dev.reedworkmc.reedwork.command.node.CommandNodeDefinition;
import dev.reedworkmc.reedwork.logger.PaperLogger;

import java.util.List;

public final class CommandNodeDebugPrinter {

    private final PaperLogger logger;

    public CommandNodeDebugPrinter(PaperLogger logger) {
        this.logger = logger;
    }

    public void print(List<CommandNodeDefinition> nodes) {
        logger.info("Command Tree:");

        for (CommandNodeDefinition node : nodes) {
            print(node, 0);
        }
    }

    private void print(CommandNodeDefinition node, int depth) {
        String indent = "  ".repeat(depth);

        logger.info(
                indent +
                        "- " +
                        (node.isArgument()
                                ? "<" + node.name() + ">"
                                : node.name())
        );

        for (CommandNodeDefinition child : node.nodes()) {
            print(child, depth + 1);
        }
    }
}