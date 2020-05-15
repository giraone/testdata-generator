package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.EnumLanguage;
import com.giraone.testdata.generator.GeneratorConfiguration;

/**
 * This class adds a phone number string value to the person.
 */
@SuppressWarnings("unused")
public class FieldEnhancerPhoneNumber implements FieldEnhancer {

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        final String phoneNumber = randomPhoneNumber(configuration);
        setAdditionalField(configuration, person, FieldConstants.phoneNumber, phoneNumber);
    }

    protected String randomPhoneNumber(GeneratorConfiguration configuration) {

        if (configuration.language == EnumLanguage.en) {
            return "+44 " + (1000 + RANDOM.nextInt(9000)) + " " + ((1000 + RANDOM.nextInt(9000)));
        } else {
            return "+49 " + (1000 + RANDOM.nextInt(9000)) + " " + ((1000 + RANDOM.nextInt(9000)));
        }
    }
}