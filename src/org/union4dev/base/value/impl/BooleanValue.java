package org.union4dev.base.value.impl;

import org.union4dev.base.value.AbstractValue;

public class BooleanValue extends AbstractValue<Boolean> {

    public BooleanValue(String name, boolean enabled) {
        super(name);
        this.setValue(enabled);
    }

}
