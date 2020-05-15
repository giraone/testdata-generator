package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.EnumGender;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.time.format.DateTimeFormatter;

/**
 * This class adds an "ssn" string value to the person, which ist derived from dateOfBirth, gender
 */
@SuppressWarnings("unused")
public class FieldEnhancerSsn implements FieldEnhancer {

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        String ssn = ssnGermanFromPerson(configuration, person);
        setAdditionalField(configuration, person, FieldConstants.ssn, ssn);
    }

    protected String ssnGermanFromPerson(GeneratorConfiguration configuration, Person person) {

        String birthName = (String) person.getAdditionalField(configuration, FieldConstants.birthName);
        if (birthName == null) {
            birthName = person.getSurname();
        }
        String isoDateOfBirth = (String) person.getAdditionalField(configuration, FieldConstants.dateOfBirth);
        return SsnGerman.build(birthName, DateTimeFormatter.ISO_DATE.parse(isoDateOfBirth), EnumGender.male == person.getGender());
    }

    protected String randomSsnPrefix(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("ssn-prefix-" + configuration.language + ".txt");
    }
}