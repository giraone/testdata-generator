package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.util.Random;

/**
 * The interface is intended to bei implemented by additional classes that generate additional fields.
 */
public interface FieldEnhancer {

    static final Random RANDOM = new Random();

    void addFields(GeneratorConfiguration configuration, Person person, String field);

    default void setAdditionalField(GeneratorConfiguration configuration, Person person, String field, String value) {

        if (configuration.getAliasReader() != null) {
            person.setAdditionalField(configuration.getAliasReader().getFieldName(field), value);
        } else {
            person.setAdditionalField(field, value);
        }
    }
}
