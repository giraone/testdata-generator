package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.util.Random;

/**
 * This class adds a "placeOfBirth" string value (city) to the person in 50% of all cases.
 */
@SuppressWarnings("unused")
public class FieldEnhancerPlaceOfBirth implements FieldEnhancer {

    private static final Random RANDOM = new Random();

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        if (RANDOM.nextBoolean()) {
            final String[] randomCityAndPostCode = FieldEnhancerPostalAddress.randomCityAndPostCode(configuration);
            setAdditionalField(configuration, person, FieldConstants.placeOfBirth, randomCityAndPostCode[0]);
        }
    }
}
