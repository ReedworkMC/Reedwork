package dev.okaj.paper.common.command.parameter;

import dev.okaj.paper.common.command.parameter.resolver.BlockPositionParameterResolver;
import dev.okaj.paper.common.command.parameter.resolver.EntityCommandParameterResolver;
import dev.okaj.paper.common.command.parameter.resolver.PlayerCommandParameterResolver;
import dev.okaj.paper.common.command.parameter.resolver.primitiv.*;

public final class DefaultParameterResolvers {

    public static void register(ParameterResolverRegistry registry) {
        registry.register(new StringCommandParameterResolver());
        registry.register(new IntegerCommandParameterResolver());
        registry.register(new LongCommandParameterResolver());
        registry.register(new FloatCommandParameterResolver());
        registry.register(new DoubleCommandParameterResolver());
        registry.register(new BooleanCommandParameterResolver());
        registry.register(new UUIDCommandParameterResolver());

        registry.register(new PlayerCommandParameterResolver());
        registry.register(new EntityCommandParameterResolver());
        registry.register(new BlockPositionParameterResolver());
    }
}