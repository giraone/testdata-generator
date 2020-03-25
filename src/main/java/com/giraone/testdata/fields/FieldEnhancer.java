package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.GeneratorConfiguration;

/**
 * The interface is intended to bei implemented by additional classes that generate additional fields.
 */
public interface FieldEnhancer {

    void addFields(GeneratorConfiguration configuration, Person person, String field);

    default void setAdditionalField(GeneratorConfiguration configuration, Person person, String field, String value) {

        if (configuration.getAliasReader() != null) {
            person.setAdditionalField(configuration.getAliasReader().getFieldName(field), value);
        } else {
            person.setAdditionalField(field, value);
        }
    }
}
