package com.giraone.testdata.generator;

import com.giraone.testdata.Person;
import com.giraone.testdata.fields.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class GeneratorPersonTest {

    private static Generator generatorDE;
    private static Generator generatorEN;

    @BeforeClass
    public static void init()
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
    public void generatePersonEn() {
        Person person = generatorEN.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getGender());
        Assert.assertNotNull(person.getGivenName());
        Assert.assertNotNull(person.getSurname());
    }

    @Test
    public void generatePersonDe() {
        Person person = generatorDE.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getGender());
        Assert.assertNotNull(person.getGivenName());
        Assert.assertNotNull(person.getSurname());
    }

    @Test
    public void generatePersonsEn() {
        List<Person> personList = generatorEN.randomPersons(0, 10);
        Assert.assertNotNull(personList);
        Assert.assertEquals(10, personList.size());
    }

    @Test
    public void generatePersonsDe() {
        List<Person> personList = generatorDE.randomPersons(0, 10);
        Assert.assertNotNull(personList);
        Assert.assertEquals(10, personList.size());
    }

    //- ID generation --------------------------------------------------------------------------------------------------

    @Test
    public void testThatUuidIsGenerated() {
        generatorEN.getConfiguration().idType = EnumIdType.uuid;
        Person person = generatorEN.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getId());
        // Sth. like 26297343-cc92-4363-ad45-ee52d091c286
        Assert.assertTrue("\"" + person.getId() + "\" is not a UUID string",
                person.getId().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    public void testThatSequenceIdIsGenerated() {
        generatorEN.getConfiguration().idType = EnumIdType.sequence;
        generatorEN.getConfiguration().withIndex = true;
        Person person = generatorEN.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getId());
        // Sth. like 1547665828000
        Assert.assertTrue("\"" + person.getId() + "\" is not a long decimal number string",
                person.getId().matches("^[0-9]{1,20}$"));
        Assert.assertEquals(person.getId(), Long.toUnsignedString(Long.parseLong(person.getId())));
    }

    //- field generation -----------------------------------------------------------------------------------------------

    @Test
    public void testThatDateOfBirthIsGenerated() {
        generatorEN.getConfiguration().additionalFields.put(FieldConstants.dateOfBirth, new FieldEnhancerDateOfBirth());
        Person person = generatorEN.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField(FieldConstants.dateOfBirth));
        // Sth. like 19410809
        Assert.assertTrue("Not a ISO date string",
                person.getAdditionalField(FieldConstants.dateOfBirth).toString().matches("^[0-9]{8}$"));

    }

    @Test
    public void testThatGermanPostalAddressIsGenerated() {
        generatorDE.getConfiguration().additionalFields.put("postalAddress", new FieldEnhancerPostalAddress());
        Person person = generatorDE.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField(FieldConstants.city));
        Assert.assertNotNull(person.getAdditionalField(FieldConstants.streetAddress));
    }

    @Test
    public void testThatEmailIsGenerated() {
        generatorDE.getConfiguration().additionalFields.put(FieldConstants.email, new FieldEnhancerEmail());
        Person person = generatorDE.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField(FieldConstants.email));
        Assert.assertTrue("Not an email string",
                person.getAdditionalField(FieldConstants.email).toString().matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$"));
    }

    @Test
    public void testThatGermanIbanIsGenerated() {
        generatorDE.getConfiguration().additionalFields.put(FieldConstants.iban, new FieldEnhancerIban());
        Person person = generatorDE.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField(FieldConstants.iban));
        Assert.assertTrue("Not a german IBAN string",
                person.getAdditionalField(FieldConstants.iban).toString().matches("^DE[0-9]{20}$"));
    }

    @Test
    public void testThatBritishIbanIsGenerated() {
        generatorEN.getConfiguration().additionalFields.put(FieldConstants.iban, new FieldEnhancerIban());
        Person person = generatorEN.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField(FieldConstants.iban));
        Assert.assertTrue("Not a british IBAN string",
                person.getAdditionalField(FieldConstants.iban).toString().matches("^GB[0-9]{2}[A-Z]{4}[0-9]{14}$"));
    }
}