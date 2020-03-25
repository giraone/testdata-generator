package com.giraone.testdata.generator;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GeneratorTest {

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

    //- counting entries test ------------------------------------------------------------------------------------------

    @Test
    public void countEnglishMaleGivenName() {
        int count = generatorEN.getNumberOfEntriesGivenName(EnumGender.male);
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countEnglishFemaleGivenName() {
        int count = generatorEN.getNumberOfEntriesGivenName(EnumGender.female);
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countGermanMaleGivenName() {
        int count = generatorDE.getNumberOfEntriesGivenName(EnumGender.male);
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countGermanFemaleGivenName() {
        int count = generatorDE.getNumberOfEntriesGivenName(EnumGender.female);
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countEnglishSurname() {
        int count = generatorEN.getNumberOfEntriesSurname();
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    @Test
    public void countGermanSurname() {
        int count = generatorDE.getNumberOfEntriesSurname();
        Assert.assertTrue("Count is not greater than 10", count > 10);
    }

    //- Basic given name tests -----------------------------------------------------------------------------------------

    @Test
    public void createOneEnglishMaleGivenName() {
        String name = generatorDE.randomGivenName(EnumGender.male);
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    @Test
    public void createOneGermanMaleGivenName() {
        String name = generatorDE.randomGivenName(EnumGender.male);
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    @Test
    public void createOneEnglishFemaleGivenName() {
        String name = generatorEN.randomGivenName(EnumGender.female);
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    @Test
    public void createOneGermanFemaleGivenName() {
        String name = generatorDE.randomGivenName(EnumGender.female);
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    //- Basic surname tests --------------------------------------------------------------------------------------------

    @Test
    public void createOneEnglishFemaleSurname() {
        String name = generatorEN.randomSurname();
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    @Test
    public void createOneGermanFemaleSurname() {
        String name = generatorDE.randomSurname();
        Assert.assertNotNull(name);
        Assert.assertTrue("Name is empty or only one character", name.length() > 1);
    }

    //- Check often used -----------------------------------------------------------------------------------------------

    @Test
    public void checkOftenUsedEnglishMaleGivenName() {
        checkOftenUsedEnglish(generatorEN, EnumGender.male, "James");
    }

    @Test
    public void checkOftenUsedGermanMaleGivenName() {
        checkOftenUsedEnglish(generatorDE, EnumGender.male, "Peter");
    }

    @Test
    public void checkOftenUsedEnglishFemaleGivenName() {
        checkOftenUsedEnglish(generatorEN, EnumGender.female, "Mary");
    }

    @Test
    public void checkOftenUsedGermanFemaleGivenName() {
        checkOftenUsedEnglish(generatorDE, EnumGender.female, "Maria");
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
