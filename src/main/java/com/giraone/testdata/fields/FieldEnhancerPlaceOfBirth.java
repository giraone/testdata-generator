package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.GeneratorConfiguration;

/**
 * This class adds a "placeOfBirth" string value (city) to the person in 50% of all cases.
 */
@SuppressWarnings("unused")
public class FieldEnhancerPlaceOfBirth implements FieldEnhancer {

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        if (RANDOM.nextBoolean()) {
            final String[] randomCityAndPostCode = FieldEnhancerPostalAddress.randomCityAndPostCode(configuration);
            setAdditionalField(configuration, person, FieldConstants.placeOfBirth, randomCityAndPostCode[0]);
        }
    }
}
