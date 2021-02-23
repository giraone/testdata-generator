package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;

/**
 * This class adds a "postal address" (street, postalCode, city) to the person.
 */
@SuppressWarnings("unused")
public class FieldEnhancerPostalAddress implements FieldEnhancer {

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        final String[] randomCityAndPostCode= randomCityAndPostCode(configuration);
        setAdditionalField(configuration, person, FieldConstants.city, randomCityAndPostCode[0]);
        setAdditionalField(configuration, person, FieldConstants.postalCode, randomCityAndPostCode[1]);
        final String street = randomStreet(configuration);
        final String houseNumber = randomHouseNumber(configuration);

        if (configuration.containsAdditionalField(FieldConstants.street)) {
            setAdditionalField(configuration, person, FieldConstants.street, street);
            if (configuration.containsAdditionalField(FieldConstants.houseNumber)) {
                setAdditionalField(configuration, person, FieldConstants.houseNumber, houseNumber);
            }
        } else {
            setAdditionalField(configuration, person, FieldConstants.streetAddress, street + " " + houseNumber);
        }
    }

    /**
     * Return a string array, where the 1st element is the city, the second is a matching postal code for the city
     * @param configuration the country configuration
     * @return 2-dimensional string array
     */
    protected static String[] randomCityAndPostCode(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("city+postcode-" + configuration.country + ".txt").split(",");
    }

    protected String randomStreet(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("street-" + configuration.language + ".txt");
    }

    protected String randomHouseNumber(GeneratorConfiguration configuration) {

        return Generator.randomFromWeightedFile("house-number-" + configuration.language + ".txt");
    }
}
