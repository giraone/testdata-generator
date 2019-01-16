package com.giraone.testdata.generator;

import com.giraone.testdata.Person;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class GeneratorPersonTest {

    private static Generator generator_de = new Generator(EnumLanguage.de);
    private static Generator generator_en = new Generator(EnumLanguage.en);

    @BeforeClass
    public static void init() {
    }

    //- person generation ----------------------------------------------------------------------------------------------

    @Test
    public void generatePersonEn() {
        Person person = generator_en.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getGender());
        Assert.assertNotNull(person.getGivenName());
        Assert.assertNotNull(person.getSurname());
    }

    @Test
    public void generatePersonDe() {
        Person person = generator_de.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getGender());
        Assert.assertNotNull(person.getGivenName());
        Assert.assertNotNull(person.getSurname());
    }

    @Test
    public void generatePersonsEn() {
        List<Person> personList = generator_en.randomPersons(0, 10);
        Assert.assertNotNull(personList);
        Assert.assertEquals(10, personList.size());
    }

    @Test
    public void generatePersonsDe() {
        List<Person> personList = generator_de.randomPersons(0, 10);
        Assert.assertNotNull(personList);
        Assert.assertEquals(10, personList.size());
    }

    //- ID generation --------------------------------------------------------------------------------------------------

    @Test
    public void testThatUuidIsGenerated() {
        generator_en.setIdType(EnumIdType.uuid);
        Person person = generator_en.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getId());
        // Sth. like 26297343-cc92-4363-ad45-ee52d091c286
        Assert.assertTrue("Not a UUID string",
                person.getId().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"));
    }

    @Test
    public void testThatSequenceIdIsGenerated() {
        generator_en.setIdType(EnumIdType.sequence);
        generator_en.setWithIndex(true);
        Person person = generator_en.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getId());
        // Sth. like 1547665828000
        Assert.assertTrue("Not a long decimal number string", person.getId().matches("[0-9]{1,20}"));
        Assert.assertEquals(person.getId(), Long.toUnsignedString(Long.parseLong(person.getId())));
    }

    //- field generation -----------------------------------------------------------------------------------------------

    @Test
    public void testThatDateOfBirthGenerated() {
        generator_en.addAdditionalField(EnumField.dateOfBirth);
        Person person = generator_en.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        System.err.println(person.getAdditionalField(EnumField.dateOfBirth));
        Assert.assertNotNull(person.getAdditionalField(EnumField.dateOfBirth));
        // Sth. like 19410809
        Assert.assertTrue("Not a ISO date string",
                person.getAdditionalField(EnumField.dateOfBirth).matches("[0-9]{8}"));

    }
}