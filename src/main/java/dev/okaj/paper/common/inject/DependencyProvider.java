package dev.okaj.paper.common.inject;

public interface DependencyProvider {
    <T> T get(Class<T> type);
    <T> T get(Class<T> type, String name);
    <T> T create(Class<T> type);
}