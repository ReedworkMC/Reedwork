package dev.reedworkmc.reedwork.inject.provider;

public class SingletonProvider<T> implements Provider<T> {

    private final T instance;

    public SingletonProvider(T instance) {
        this.instance = instance;
    }

    @Override
    public T get() {
        return instance;
    }
}
