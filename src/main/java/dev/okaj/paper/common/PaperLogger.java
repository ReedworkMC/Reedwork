package dev.okaj.paper.common;

public interface PaperLogger {

    void info(String message);
    void warn(String message);
    void error(String message, Throwable throwable);
}
