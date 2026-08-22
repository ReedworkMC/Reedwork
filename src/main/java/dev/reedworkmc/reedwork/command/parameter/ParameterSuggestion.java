package dev.reedworkmc.reedwork.command.parameter;

import com.mojang.brigadier.suggestion.SuggestionProvider;
import dev.reedworkmc.reedwork.inject.injector.InjectorDependencyProvider;
import io.papermc.paper.command.brigadier.CommandSourceStack;

public final class ParameterSuggestion {

    private final Class<? extends SuggestionProvider<CommandSourceStack>> type;
    private SuggestionProvider<CommandSourceStack> provider;

    public ParameterSuggestion(Class<? extends SuggestionProvider<CommandSourceStack>> type) {
        this.type = type;
    }

    public Class<? extends SuggestionProvider<CommandSourceStack>> type() {
        return type;
    }

    public SuggestionProvider<CommandSourceStack> resolve(InjectorDependencyProvider injector) {
        return injector.get(type);
    }
}