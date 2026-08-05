package dev.okaj.paper.common.inject.exception;

public final class DependencyException extends RuntimeException {

    public DependencyException(String message) {
        super(message);
    }

    public DependencyException(String message, Throwable cause) {
        super(message, cause);
    }
}