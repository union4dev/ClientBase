package org.union4dev.base.annotations.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attached {@link org.union4dev.base.module.ModuleHandle}
 * Add this annotation to the method to that this class will be invoked at module disable
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Disable {
}
