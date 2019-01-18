package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.util.Random;

/**
 * This class adds a "postal address" (street, postalCode, city) to the person.
 */
public class FieldEnhancerPostalAddress implements FieldEnhancer {

    private static final Random RANDOM = new Random();

    public void addFields(GeneratorConfiguration configuration, String field, Person person) {

        final String postalCode = randomPostalCode(configuration);
        person.setAdditionalField("postalCode", postalCode);
        final String city = randomCity(configuration);
        person.setAdditionalField("city", city);
        final String street = randomStreet(configuration);
        final String houseNumber = randomHouseNumber(configuration);
        person.setAdditionalField("street", street + " " + houseNumber);
    }

    protected String randomPostalCode(GeneratorConfiguration configuration) {

        // TODO: fetch real germam PLZs
        return String.format("%5d", RANDOM.nextInt(89999) + 10000);
    }

    protected String randomCity(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("city-" + configuration.country + "-" + configuration.language + ".txt");
    }

    protected String randomStreet(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("street-" + configuration.language + ".txt");
    }

    protected String randomHouseNumber(GeneratorConfiguration configuration) {

        return Generator.randomFromWeightedFile("house-number-" + configuration.language + ".txt");
    }
}
