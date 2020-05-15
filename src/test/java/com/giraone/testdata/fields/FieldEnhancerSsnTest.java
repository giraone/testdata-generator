package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.EnumGender;
import com.giraone.testdata.generator.EnumLanguage;
import com.giraone.testdata.generator.GeneratorConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FieldEnhancerSsnTest {

    private static GeneratorConfiguration de = new GeneratorConfiguration();
    private static GeneratorConfiguration en = new GeneratorConfiguration();

    private FieldEnhancerSsn cut = new FieldEnhancerSsn();

    @BeforeAll
    public static void init()
    {
        de.language = EnumLanguage.de;
        en.language = EnumLanguage.en;
    }

    @Test
    public void testThatGermanSsnIsGeneratedCorrectlyForMales() {

        Person person = new Person("Erwin", "Doe", EnumGender.male);
        person.setAdditionalField(de, FieldConstants.dateOfBirth, "2000-12-31");
        String ssn = cut.ssnGermanFromPerson(de, person);
        assertThat(ssn).isNotNull();
        assertThat(ssn).matches("[0-9]{2}311200D[0-4][0-9][0-9]");
    }

    @Test
    public void testThatGermanSsnIsGeneratedCorrectlyForFemales() {

        Person person = new Person("Erika", "Mustermann", EnumGender.female);
        person.setAdditionalField(de, FieldConstants.dateOfBirth, "2000-12-31");
        String ssn = cut.ssnGermanFromPerson(de, person);
        assertThat(ssn).isNotNull();
        assertThat(ssn).matches("[0-9]{2}311200M[5-9][0-9][0-9]");
    }

    @Test
    public void testThatGermanSsnIsGeneratedCorrectlyWithBirthName() {

        Person person = new Person("Erika", "Mustermann", EnumGender.female);
        person.setAdditionalField(de, FieldConstants.dateOfBirth, "2000-12-31");
        person.setAdditionalField(de, FieldConstants.birthName, "Wagner");
        String ssn = cut.ssnGermanFromPerson(de, person);
        assertThat(ssn).isNotNull();
        assertThat(ssn).matches("[0-9]{2}311200W[5-9][0-9][0-9]");
    }
}