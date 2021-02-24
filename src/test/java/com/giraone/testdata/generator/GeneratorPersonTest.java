package com.giraone.testdata.generator;

import com.giraone.testdata.Person;
import com.giraone.testdata.fields.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GeneratorPersonTest {

    private static Generator generatorDE;
    private static Generator generatorEN;

    @BeforeAll
    static void init()
    {
        GeneratorConfiguration de = new GeneratorConfiguration();
        de.language = EnumLanguage.de;
        generatorDE = new Generator(de);

        GeneratorConfiguration en = new GeneratorConfiguration();
        en.language = EnumLanguage.en;
        generatorEN = new Generator(en);
    }

    //- person generation ----------------------------------------------------------------------------------------------

    @Test
    void generatePersonEn() {
        Person person = generatorEN.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getGender()).isNotNull();
        assertThat(person.getGivenName()).isNotEmpty();
        assertThat(person.getSurname()).isNotEmpty();
    }

    @Test
    void generatePersonDe() {
        Person person = generatorDE.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getGender()).isNotNull();
        assertThat(person.getGivenName()).isNotEmpty();
        assertThat(person.getSurname()).isNotEmpty();
    }

    @Test
    void generatePersonsEn() {
        List<Person> personList = generatorEN.randomPersons(0, 10);
        assertThat(personList).isNotNull();
        assertThat(personList.size()).isEqualTo(10);
        assertThat(personList).extracting(Person::getGender).isNotNull();
        assertThat(personList).extracting(Person::getGivenName).isNotEmpty();
        assertThat(personList).extracting(Person::getSurname).isNotEmpty();
    }

    @Test
    void generatePersonsDe() {
        List<Person> personList = generatorDE.randomPersons(0, 10);
        assertThat(personList).isNotNull();
        assertThat(personList.size()).isEqualTo(10);
        assertThat(personList).extracting(Person::getGender).isNotNull();
        assertThat(personList).extracting(Person::getGivenName).isNotEmpty();
        assertThat(personList).extracting(Person::getSurname).isNotEmpty();
    }

    //- ID generation --------------------------------------------------------------------------------------------------

    @Test
    void testThatUuidIsGenerated() {
        generatorEN.getConfiguration().idType = EnumIdType.uuid;
        Person person = generatorEN.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
        // Sth. like 26297343-cc92-4363-ad45-ee52d091c286
        assertThat(person.getId()).matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }

    @Test
    void testThatSequenceIdIsGenerated() {
        generatorEN.getConfiguration().idType = EnumIdType.sequence;
        generatorEN.getConfiguration().withIndex = true;
        Person person = generatorEN.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getId()).isNotNull();
        // Sth. like 1547665828000
        assertThat(person.getId()).matches("^[0-9]{1,20}$");
        assertThat(person.getId()).isEqualTo(Long.toUnsignedString(Long.parseLong(person.getId())));
    }

    //- field generation -----------------------------------------------------------------------------------------------

    @Test
    void testThatDateOfBirthIsGenerated() {
        generatorEN.getConfiguration().addAdditionalField(FieldConstants.dateOfBirth, new FieldEnhancerDateOfBirth());
        Person person = generatorEN.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getAdditionalFields()).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.dateOfBirth)).isNotNull();
        // Sth. like 19410809
        assertThat(person.getAdditionalFields().get(FieldConstants.dateOfBirth).toString()).matches("^[0-9]{8}$");
    }

    @Test
    void testThatGermanPostalAddressIsGenerated() {
        generatorDE.getConfiguration().addAdditionalField("postalAddress", new FieldEnhancerPostalAddress());
        Person person = generatorDE.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getAdditionalFields()).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.city)).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.streetAddress)).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.state)).isNotNull();
    }

    @Test
    void testThatEmailIsGenerated() {

        generatorDE.getConfiguration().addAdditionalField(FieldConstants.email, new FieldEnhancerEmail());
        Person person = generatorDE.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getAdditionalFields()).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.email)).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.email).toString())
            .matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$");
    }

    @Test
    void testThatGermanIbanIsGenerated() {
        generatorDE.getConfiguration().addAdditionalField(FieldConstants.iban, new FieldEnhancerIban());
        Person person = generatorDE.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getAdditionalFields()).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.iban)).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.iban).toString())
            .matches("^DE[0-9]{20}$");
    }

    @Test
    void testThatBritishIbanIsGenerated() {
        generatorEN.getConfiguration().addAdditionalField(FieldConstants.iban, new FieldEnhancerIban());
        Person person = generatorEN.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getAdditionalFields()).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.iban)).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.iban).toString())
            .matches("^GB[0-9]{2}[A-Z]{4}[0-9]{14}$");
    }

    @Test
    void testThatGermanPhoneNumberIsGenerated() {
        generatorDE.getConfiguration().addAdditionalField(FieldConstants.phoneNumber, new FieldEnhancerPhoneNumber());
        Person person = generatorDE.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getAdditionalFields()).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.phoneNumber)).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.phoneNumber).toString())
                .matches("^[+]49[0-9 ]+$");
    }

    @Test
    void testThatBritishPhoneNumberIsGenerated() {
        generatorEN.getConfiguration().addAdditionalField(FieldConstants.phoneNumber, new FieldEnhancerPhoneNumber());
        Person person = generatorDE.randomPerson();
        assertThat(person).isNotNull();
        assertThat(person.getAdditionalFields()).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.phoneNumber)).isNotNull();
        assertThat(person.getAdditionalFields().get(FieldConstants.phoneNumber).toString())
                .matches("^[+]44[0-9 ]+$");
    }
}