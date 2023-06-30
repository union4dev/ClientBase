package org.union4dev.base.annotations.module;

import org.union4dev.base.module.handlers.ModuleHandle;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Attached {@link ModuleHandle}
 * Add this annotation to the method to that this class will be invoked at module enable
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Enable {
}
