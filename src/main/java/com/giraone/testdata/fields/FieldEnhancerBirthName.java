package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;

/**
 * This class adds a "birthName" string value (surname) to the person in 50% of all cases.
 */
@SuppressWarnings("unused")
public class FieldEnhancerBirthName implements FieldEnhancer {

    private Generator generator;

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        if (generator == null) {
            generator = new Generator(configuration);
        }
        if (RANDOM.nextBoolean()) {
            final String randomSurname = generator.randomSurname();
            setAdditionalField(configuration, person, FieldConstants.birthName, randomSurname
            );
        }
    }
}
