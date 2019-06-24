package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class adds a "iban" (International Bank Account Number) string value to the person.
 */
public class FieldEnhancerIban implements FieldEnhancer {

    public void addFields(GeneratorConfiguration configuration, String field, Person person) {

        final String iban = randomIban(configuration);
        person.setAdditionalField("iban", iban);
    }

    protected String randomIban(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("iban-" + configuration.language + ".txt");
    }
}