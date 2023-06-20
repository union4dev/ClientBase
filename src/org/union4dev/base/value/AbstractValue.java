package org.union4dev.base.value;

import java.util.function.BooleanSupplier;

public abstract class AbstractValue<T> {
    private String name;
    private T value;
    protected BooleanSupplier hide;

    protected AbstractValue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHideSupplier(BooleanSupplier hide) {
        this.hide = hide;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isHidden() {
        return hide.getAsBoolean();
    }
}
