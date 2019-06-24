package com.giraone.testdata.generator;

import com.giraone.testdata.Person;
import com.giraone.testdata.fields.FieldEnhancerDateOfBirth;
import com.giraone.testdata.fields.FieldEnhancerEmail;
import com.giraone.testdata.fields.FieldEnhancerIban;
import com.giraone.testdata.fields.FieldEnhancerPostalAddress;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class GeneratorPersonTest {

    private static Generator generator_de;
    private static Generator generator_en;

    @BeforeClass
    public static void init()
    {
        GeneratorConfiguration de = new GeneratorConfiguration();
        de.language = EnumLanguage.de;
        generator_de = new Generator(de);

        GeneratorConfiguration en = new GeneratorConfiguration();
        en.language = EnumLanguage.en;
        generator_en = new Generator(en);
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
        generator_en.getConfiguration().idType = EnumIdType.uuid;
        Person person = generator_en.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getId());
        // Sth. like 26297343-cc92-4363-ad45-ee52d091c286
        Assert.assertTrue("\"" + person.getId() + "\" is not a UUID string",
                person.getId().matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"));
    }

    @Test
    public void testThatSequenceIdIsGenerated() {
        generator_en.getConfiguration().idType = EnumIdType.sequence;
        generator_en.getConfiguration().withIndex = true;
        Person person = generator_en.randomPerson();
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
        generator_en.getConfiguration().additionalFields.put("dateOfBirth", new FieldEnhancerDateOfBirth());
        Person person = generator_en.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField("dateOfBirth"));
        // Sth. like 19410809
        Assert.assertTrue("Not a ISO date string",
                person.getAdditionalField("dateOfBirth").matches("^[0-9]{8}$"));

    }

    @Test
    public void testThatGermanPostalAddressIsGenerated() {
        generator_de.getConfiguration().additionalFields.put("postalAddress", new FieldEnhancerPostalAddress());
        Person person = generator_de.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField("city"));
    }

    @Test
    public void testThatEmailIsGenerated() {
        generator_de.getConfiguration().additionalFields.put("email", new FieldEnhancerEmail());
        Person person = generator_de.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField("email"));
        Assert.assertTrue("Not an email string",
                person.getAdditionalField("email").matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"));
    }

    @Test
    public void testThatGermanIbanIsGenerated() {
        generator_de.getConfiguration().additionalFields.put("iban", new FieldEnhancerIban());
        Person person = generator_de.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField("iban"));
        Assert.assertTrue("Not a german IBAN string",
                person.getAdditionalField("iban").matches("^DE[0-9]{20}$"));
    }

    @Test
    public void testThatBritishIbanIsGenerated() {
        generator_en.getConfiguration().additionalFields.put("iban", new FieldEnhancerIban());
        Person person = generator_en.randomPerson();
        Assert.assertNotNull(person);
        Assert.assertNotNull(person.getAdditionalFields());
        Assert.assertNotNull(person.getAdditionalField("iban"));
        Assert.assertTrue("Not a british IBAN string",
                person.getAdditionalField("iban").matches("^GB[0-9]{2}[A-Z]{4}[0-9]{14}$"));
    }
}