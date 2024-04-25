package org.union4dev.base.annotations.system;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind when startup
 *
 * @author cubk
 */
@Target(value={ElementType.TYPE})
@Retention(value= RetentionPolicy.RUNTIME)
public @interface Binding {
    int value();
}
