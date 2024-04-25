package org.union4dev.base.annotations.system;

import org.union4dev.base.module.Category;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Module {
    String value();
    Category category();
}
