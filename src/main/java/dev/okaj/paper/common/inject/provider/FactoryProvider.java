package dev.okaj.paper.common.inject.provider;

import java.util.function.Supplier;

public class FactoryProvider<T> implements Provider<T> {

    private final Supplier<? extends T> supplier;

    public FactoryProvider(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T get() {
        return supplier.get();
    }
}
