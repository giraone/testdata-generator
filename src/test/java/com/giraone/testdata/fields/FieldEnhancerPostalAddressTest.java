package com.giraone.testdata.fields;

import com.giraone.testdata.generator.EnumLanguage;
import com.giraone.testdata.generator.GeneratorConfiguration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FieldEnhancerPostalAddressTest {

    private static GeneratorConfiguration de = new GeneratorConfiguration();
    private static GeneratorConfiguration en = new GeneratorConfiguration();

    @BeforeClass
    public static void init()
    {
        de.language = EnumLanguage.de;
        en.language = EnumLanguage.en;
    }

    @Test
    public void testThatGermanPostalAddressCityIsGenerated() {

        FieldEnhancerPostalAddress f = new FieldEnhancerPostalAddress();
        String[] value = f.randomCityAndPostCode(de);
        Assert.assertNotNull(value);
        Assert.assertNotNull(value[0]);
        Assert.assertNotNull(value[1]);
    }

    @Test
    public void testThatGermanPostalAddressStreetIsGenerated() {

        FieldEnhancerPostalAddress f = new FieldEnhancerPostalAddress();
        String value = f.randomStreet(de);
        Assert.assertNotNull(value);
    }
}