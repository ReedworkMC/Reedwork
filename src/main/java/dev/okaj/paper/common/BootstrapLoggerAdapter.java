package dev.okaj.paper.common;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;

public class BootstrapLoggerAdapter implements PaperLogger{

    private final ComponentLogger logger;

    public BootstrapLoggerAdapter(ComponentLogger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
