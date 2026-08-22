package dev.reedworkmc.reedwork.module;

import dev.reedworkmc.reedwork.app.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {

    private final List<ReedworkModule> modules = new ArrayList<>();

    public void install(ReedworkModule module) {
        register(module);
    }

    public void register(ReedworkModule module) {
        modules.add(module);
    }

    public void initialize(ApplicationContext context) {
        for (ReedworkModule module : modules) {
            module.initialize(context);
        }
    }
}