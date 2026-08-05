package dev.okaj.paper.common.inject.injector;

public interface InjectorDependencyProvider {
    <T> T get(Class<T> type);
    <T> T get(Class<T> type, String name);
    <T> T create(Class<T> type);
}