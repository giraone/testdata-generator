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
    public static void init()
    {
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
}
