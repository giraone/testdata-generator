package com.giraone.testdata.generator;

import java.util.Arrays;

public class FieldSpec {

    private String name;
    private String[] values;
    private EnumJsonDataType jsonDataType;
    private boolean withNullValues;
    private int withNullPercentage;
    private boolean randomNumber;
    private int randomMin = 0;
    private int randomMax = 1000;
    private String randomFormat = "%04d";

    public FieldSpec(String name, String[] values, EnumJsonDataType jsonDataType, boolean withNullValues,
                     int withNullPercentage, boolean randomNumber, int randomMin, int randomMax, String randomFormat) {
        this.name = name;
        this.values = values;
        this.jsonDataType = jsonDataType;
        this.withNullValues = withNullValues;
        this.withNullPercentage = withNullPercentage;
        this.randomNumber = randomNumber;
        this.randomMin = randomMin;
        this.randomMax = randomMax;
        this.randomFormat = randomFormat;
    }

    public FieldSpec(String name, String[] values, EnumJsonDataType jsonDataType) {
        this.name = name;
        this.values = values;
        this.jsonDataType = jsonDataType;
        this.withNullValues = false;
        this.withNullPercentage = 50;
        this.randomNumber = false;
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

    @SuppressWarnings("unused")
    public void setValues(String[] values) {
        this.values = values;
    }

    public EnumJsonDataType getJsonDataType() {
        return jsonDataType;
    }

    @SuppressWarnings("unused")
    public void setJsonDataType(EnumJsonDataType jsonDataType) {
        this.jsonDataType = jsonDataType;
    }

    public boolean isWithNullValues() {
        return withNullValues;
    }

    @SuppressWarnings("unused")
    public void setWithNullValues(boolean withNullValues) {
        this.withNullValues = withNullValues;
    }

    public int getWithNullPercentage() {
        return withNullPercentage;
    }

    @SuppressWarnings("unused")
    public void setWithNullPercentage(int withNullPercentage) {
        this.withNullPercentage = withNullPercentage;
    }

    public boolean isRandomNumber() {
        return randomNumber;
    }

    @SuppressWarnings("unused")
    public void setRandomNumber(boolean randomNumber) {
        this.randomNumber = randomNumber;
    }

    public int getRandomMin() {
        return randomMin;
    }

    @SuppressWarnings("unused")
    public void setRandomMin(int randomMin) {
        this.randomMin = randomMin;
    }

    public int getRandomMax() {
        return randomMax;
    }

    @SuppressWarnings("unused")
    public void setRandomMax(int randomMax) {
        this.randomMax = randomMax;
    }

    public String getRandomFormat() {
        return randomFormat;
    }

    @SuppressWarnings("unused")
    public void setRandomFormat(String randomFormat) {
        this.randomFormat = randomFormat;
    }

    @Override
    public String toString() {
        return "FieldSpec{" +
                "name='" + name + '\'' +
                ", values=" + Arrays.toString(values) +
                ", jsonDataType=" + jsonDataType +
                ", withNullValues=" + withNullValues +
                ", withNullPercentage=" + withNullPercentage +
                ", randomNumber=" + randomNumber +
                ", randomMin=" + randomMin +
                ", randomMax=" + randomMax +
                ", randomFormat='" + randomFormat + '\'' +
                '}';
    }
}
