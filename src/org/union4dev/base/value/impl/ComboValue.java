package org.union4dev.base.value.impl;

import org.union4dev.base.value.AbstractValue;

import java.util.ArrayList;
import java.util.Arrays;

public class ComboValue extends AbstractValue<String> {

    private final String[] strings;
    public boolean shouldShow;


    public ComboValue(String name, String value, String... values) {
        super(name);
        this.strings = values;
        this.setValue(value);
    }

    public ArrayList<String> getAsArray() {
        return new ArrayList<>(Arrays.asList(strings));
    }

    public boolean isValid(String name) {
        for (String val : strings) if (val.equalsIgnoreCase(name)) return true;
        return false;
    }

    public boolean isMode(String value) {
        return getValue().equalsIgnoreCase(value);
    }

    @Override
    public void setValue(String value) {
        for (String val : strings) if (val.equalsIgnoreCase(value)) super.setValue(val);
    }

    public String[] getStrings() {
        return strings;
    }
}
