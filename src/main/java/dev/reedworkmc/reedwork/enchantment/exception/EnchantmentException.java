package dev.reedworkmc.reedwork.enchantment.exception;

public class EnchantmentException extends RuntimeException {
    public EnchantmentException(String message) {
        super(message);
    }

    public EnchantmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
