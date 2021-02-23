package com.giraone.testdata.fields;

import com.giraone.testdata.generator.EnumLanguage;
import com.giraone.testdata.generator.GeneratorConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FieldEnhancerPostalAddressTest {

    private static GeneratorConfiguration de = new GeneratorConfiguration();
    private static GeneratorConfiguration en = new GeneratorConfiguration();

    @BeforeAll
    public static void init()
    {
        de.language = EnumLanguage.de;
        en.language = EnumLanguage.en;
    }

    @Test
    public void testThatGermanPostalAddressCityIsGenerated() {


        String[] value = FieldEnhancerPostalAddress.randomCityAndPostCode(de);
        assertThat(value).isNotNull();
        assertThat(value[0]).isNotNull();
        assertThat(value[1]).isNotNull();
    }

    @Test
    public void testThatGermanPostalAddressStreetIsGenerated() {

        FieldEnhancerPostalAddress f = new FieldEnhancerPostalAddress();
        String value = f.randomStreet(de);
        assertThat(value).isNotNull();
        assertThat(value).isNotEmpty();
    }
}