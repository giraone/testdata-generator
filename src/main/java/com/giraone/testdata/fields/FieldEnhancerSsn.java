package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.EnumGender;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;

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

        String initial = person.getSurname().substring(0,1).toUpperCase();
        String isoDateOfBirth = (String) person.getAdditionalField(configuration, FieldConstants.dateOfBirth);
        String dateOfBirthString = isoDateOfBirth.substring(6,8) + isoDateOfBirth.substring(4,6) + isoDateOfBirth.substring(2,4);
        String genderString = String.format("%02d", EnumGender.male == person.getGender()
                ? RANDOM.nextInt(50) : 50 + RANDOM.nextInt(50));
        String checkSum = "1"; // TODO
        return randomSsnPrefix(configuration) + dateOfBirthString + initial + genderString + checkSum;
    }

    protected String randomSsnPrefix(GeneratorConfiguration configuration) {

        return Generator.randomFromFile("ssn-prefix-" + configuration.language + ".txt");
    }
}