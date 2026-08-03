package dev.okaj.paper.common.command.parameter;

import dev.okaj.paper.common.command.parameter.resolver.*;
import dev.okaj.paper.common.command.parameter.resolver.primitiv.*;

public final class DefaultParameterResolvers {

    public static void register(ParameterResolverRegistry registry) {
        // primitives
        registry.register(new BlockStateCommandParameterResolver());
        registry.register(new BooleanCommandParameterResolver());
        registry.register(new ComponentCommandParameterResolver());
        registry.register(new CriteriaCommandParameterResolver());
        registry.register(new DisplaySlotCommandParameterResolver());
        registry.register(new DoubleCommandParameterResolver());
        registry.register(new FloatCommandParameterResolver());
        registry.register(new GameModeCommandParameterResolver());
        registry.register(new HexColorCommandParameterResolver());
        registry.register(new HightMapCommandParameterResolver());
        registry.register(new IntegerCommandParameterResolver());
        registry.register(new ItemStackCommandParameterResolver());
        registry.register(new ItemStackPredicateCommandParameterResolver());
        registry.register(new KeyCommandParameterResolver());
        registry.register(new LongCommandParameterResolver());
        registry.register(new NamedTextColorCommandParameterResolver());
        registry.register(new LookAnchorCommandParameterResolver());
        registry.register(new NamespacedKeyCommandParameterResolver());
        registry.register(new StringCommandParameterResolver());
        registry.register(new StyleCommandParameterResolver());
        registry.register(new UUIDCommandParameterResolver());
        registry.register(new WorldCommandParameterResolver());

        // resolved
        registry.register(new BlockPositionParameterResolver());
        registry.register(new DoubleRangeProviderCommandParameterResolver());
        registry.register(new EntityCommandParameterResolver());
        registry.register(new EntityListCommandParameterResolver());
        registry.register(new FinePositionParameterResolver());
        registry.register(new IntegerRangeProviderCommandParameterResolver());
        registry.register(new PlayerCommandParameterResolver());
        registry.register(new PlayerListCommandParameterResolver());
        registry.register(new PlayerProfileListCommandParameterResolver());

        //todo resource(RegistryKey), resourceKey(RegistryKey)

        //missing signedMessage(), time(int mintime), templateMirror(), templateRotation()
    }
}