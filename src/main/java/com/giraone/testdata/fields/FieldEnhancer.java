package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.GeneratorConfiguration;

/**
 * The interface is intended to bei implemented by additional classes that generate additional fields.
 */
public interface FieldEnhancer {

    public void addFields(GeneratorConfiguration configuration, String field, Person person);
}
