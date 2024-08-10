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

    public String getNextValue() {
        ArrayList<String> valuesList = getAsArray();
        String currentValue = getValue();
        int index = valuesList.indexOf(currentValue);
        if (index == -1 || index == valuesList.size() - 1) {
            // 当前值不存在或者是最后一个值，则返回第一个值作为下一个值
            return valuesList.get(0);
        } else {
            // 返回下一个值
            return valuesList.get(index + 1);
        }
    }

    public String[] getStrings() {
        return strings;
    }
}
