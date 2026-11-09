package com.giraone.testdata.generator;

import com.giraone.testdata.fields.FieldEnhancer;

public class AdditionalField {

    final String name;
    final FieldEnhancer fieldEnhancer;

    public AdditionalField(String name, FieldEnhancer fieldEnhancer) {
        this.name = name;
        this.fieldEnhancer = fieldEnhancer;
    }

    public String getName() {
        return name;
    }

    public FieldEnhancer getFieldEnhancer() {
        return fieldEnhancer;
    }
}
