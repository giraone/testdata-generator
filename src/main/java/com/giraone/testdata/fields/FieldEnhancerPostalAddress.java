package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;

/**
 * This class adds a "postal address" (street, postalCode, city) to the person.
 */
public class FieldEnhancerPostalAddress implements FieldEnhancer {

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        final String[] randomCityAndPostCode= randomCityAndPostCode(configuration);
        person.setAdditionalField(configuration, FieldConstants.city, randomCityAndPostCode[0]);
        person.setAdditionalField(configuration, FieldConstants.postalCode, randomCityAndPostCode[1]);
        person.setAdditionalField(configuration, FieldConstants.state, randomCityAndPostCode[2]);
        person.setAdditionalField(configuration, FieldConstants.stateCode, randomCityAndPostCode[3]);
        final String street = randomStreet(configuration);
        final String houseNumber = randomHouseNumber(configuration);
        person.setAdditionalField(configuration, FieldConstants.streetAddress, street + " " + houseNumber);
    }

    /**
     * Return a string array, where the 1st element is the city, the second is a matching postal code for the city
     * @param configuration the country configuration
     * @return 4-dimensional string array with city, postalCode, state, stateCode
     */
    protected static String[] randomCityAndPostCode(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("city+postcode+state-" + configuration.country + ".txt").split(",");
    }

    protected static String randomStreet(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("street-" + configuration.language + ".txt");
    }

    protected static String randomHouseNumber(GeneratorConfiguration configuration) {

        return Generator.randomFromWeightedFile("house-number-" + configuration.language + ".txt");
    }

    protected static String stateCodeByPostCode(String postCode) {

        return postCode.startsWith("9") ? "9" : "5";
    }
}
