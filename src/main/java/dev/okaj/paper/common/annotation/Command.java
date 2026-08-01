package dev.okaj.paper.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Transient
public @interface Command {

    String value();

    String description() default "";

    String permission() default "";

    String[] aliases() default {};
}