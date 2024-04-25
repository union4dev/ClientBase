package org.union4dev.base.annotations.system;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String[] value();
    String usage() default "";

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Handler{}
}
