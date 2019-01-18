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
        String value = f.randomCity(de);
        Assert.assertNotNull(value);
    }

    @Test
    public void testThatGermanPostalAddressStreetIsGenerated() {

        FieldEnhancerPostalAddress f = new FieldEnhancerPostalAddress();
        String value = f.randomStreet(de);
        Assert.assertNotNull(value);
    }
}