package dev.okaj.paper.common.module;

import dev.okaj.paper.common.PaperContext;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {

    private final List<PaperModule> modules = new ArrayList<>();

    public void install(PaperModule module) {
        register(module);
    }

    public void register(PaperModule module) {
        modules.add(module);
    }

    public void initialize(PaperContext context) {
        for (PaperModule module : modules) {
            module.initialize(context);
        }
    }
}