package com.giraone.testdata.generator;

import java.util.Arrays;

public class FieldSpec {

    private String name;
    private String[] values;
    private EnumJsonDataType jsonDataType;
    private boolean withNullValues;
    private int withNullPercentage;

    public FieldSpec() {
    }

    public FieldSpec(String name, String[] values, EnumJsonDataType jsonDataType, boolean withNullValues, int withNullPercentage) {
        this.name = name;
        this.values = values;
        this.jsonDataType = jsonDataType;
        this.withNullValues = withNullValues;
        this.withNullPercentage = withNullPercentage;
    }

    public FieldSpec(String name, String[] values, EnumJsonDataType jsonDataType) {
        this.name = name;
        this.values = values;
        this.jsonDataType = jsonDataType;
        this.withNullValues = false;
        this.withNullPercentage = 50;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public EnumJsonDataType getJsonDataType() {
        return jsonDataType;
    }

    public void setJsonDataType(EnumJsonDataType jsonDataType) {
        this.jsonDataType = jsonDataType;
    }

    public boolean isWithNullValues() {
        return withNullValues;
    }

    public void setWithNullValues(boolean withNullValues) {
        this.withNullValues = withNullValues;
    }

    public int getWithNullPercentage() {
        return withNullPercentage;
    }

    public void setWithNullPercentage(int withNullPercentage) {
        this.withNullPercentage = withNullPercentage;
    }

    @Override
    public String toString() {
        return "FieldSpec{" +
            "name='" + name + '\'' +
            ", values=" + Arrays.toString(values) +
            ", jsonDataType=" + jsonDataType +
            ", isNull=" + withNullValues +
            ", isNullPercentage=" + withNullPercentage +
            '}';
    }
}
