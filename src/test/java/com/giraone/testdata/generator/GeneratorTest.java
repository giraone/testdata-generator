package com.giraone.testdata.generator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneratorTest {

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

    //- counting entries test ------------------------------------------------------------------------------------------

    @Test
    public void countEnglishMaleGivenName() {
        int count = generator_en.getNumberOfEntriesGivenName(EnumGender.male);
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countEnglishFemaleGivenName() {
        int count = generator_en.getNumberOfEntriesGivenName(EnumGender.female);
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countGermanMaleGivenName() {
        int count = generator_de.getNumberOfEntriesGivenName(EnumGender.male);
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countGermanFemaleGivenName() {
        int count = generator_de.getNumberOfEntriesGivenName(EnumGender.female);
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countEnglishSurname() {
        int count = generator_en.getNumberOfEntriesSurname();
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countGermanSurname() {
        int count = generator_de.getNumberOfEntriesSurname();
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    //- Basic given name tests -----------------------------------------------------------------------------------------

    @Test
    public void createOneEnglishMaleGivenName() {
        String name = generator_de.randomGivenName(EnumGender.male);
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    @Test
    public void createOneGermanMaleGivenName() {
        String name = generator_de.randomGivenName(EnumGender.male);
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    @Test
    public void createOneEnglishFemaleGivenName() {
        String name = generator_en.randomGivenName(EnumGender.female);
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    @Test
    public void createOneGermanFemaleGivenName() {
        String name = generator_de.randomGivenName(EnumGender.female);
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    //- Basic surname tests --------------------------------------------------------------------------------------------

    @Test
    public void createOneEnglishFemaleSurname() {
        String name = generator_en.randomSurname();
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    @Test
    public void createOneGermanFemaleSurname() {
        String name = generator_de.randomSurname();
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    //- Check often used -----------------------------------------------------------------------------------------------

    @Test
    public void checkOftenUsedEnglishMaleGivenName() {
        checkOftenUsedEnglish(generator_en, EnumGender.male, "James");
    }

    @Test
    public void checkOftenUsedGermanMaleGivenName() {
        checkOftenUsedEnglish(generator_de, EnumGender.male, "Peter");
    }

    @Test
    public void checkOftenUsedEnglishFemaleGivenName() {
        checkOftenUsedEnglish(generator_en, EnumGender.female, "Mary");
    }

    @Test
    public void checkOftenUsedGermanFemaleGivenName() {
        checkOftenUsedEnglish(generator_de, EnumGender.female, "Maria");
    }

    private void checkOftenUsedEnglish(Generator generator, EnumGender gender, String check) {
        int entries = generator.getNumberOfEntriesGivenName(gender);
        int count = 0;
        for (int i = 0; i < entries; i++)
        {
            String name = generator.randomGivenName(gender);
            if (check.equals(name)) {
                count++;
            }
        }
        Assert.assertTrue("count not greater than 5", count > 5);
    }
}
