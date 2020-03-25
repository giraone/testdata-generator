package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;

/**
 * This class adds a "iban" (International Bank Account Number) string value to the person.
 */
@SuppressWarnings("unused")
public class FieldEnhancerIban implements FieldEnhancer {

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        final String iban = randomIban(configuration);
        setAdditionalField(configuration, person, FieldConstants.iban, iban);
    }

    protected String randomIban(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("iban-" + configuration.language + ".txt");
    }
}