package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * This class adds a "dateOfBirth" string value (ISO date "yyyymmdd") to the person.
 */
public class FieldEnhancerDateOfBirth implements FieldEnhancer {

    private static final Random RANDOM = new Random();

    private static LocalDate fromLocalDate = LocalDate.parse("1930-01-01");
    private static LocalDate toLocalDate = LocalDate.now().minusYears(16);
    private static int dayLimit = (int) DAYS.between(fromLocalDate, toLocalDate);

    public void addFields(GeneratorConfiguration configuration, String field, Person person) {

        final String value = randomDateOfBirthAsIsoString();
        person.setAdditionalField(field, value);
    }

    private String randomDateOfBirthAsIsoString() {

        return DateTimeFormatter.BASIC_ISO_DATE.format(fromLocalDate.plusDays(RANDOM.nextInt(dayLimit)));
    }
}
