package dev.okaj.paper.common.inject.processor;

import java.util.List;

public interface ClassProcessor {

    void process(List<Class<?>> classes);

}