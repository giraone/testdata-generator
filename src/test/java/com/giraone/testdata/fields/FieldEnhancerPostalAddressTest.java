package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.EnumGender;
import com.giraone.testdata.generator.EnumLanguage;
import com.giraone.testdata.generator.GeneratorConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FieldEnhancerPostalAddressTest {

    private static final GeneratorConfiguration de = new GeneratorConfiguration();
    private static final GeneratorConfiguration en = new GeneratorConfiguration();

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
    public void testThatGermanPostalAddressStreetAddressIsGenerated() {

        FieldEnhancerPostalAddress f = new FieldEnhancerPostalAddress();
        String value = f.randomStreet(de);
        assertThat(value).isNotNull();
        assertThat(value).isNotEmpty();
    }

    @Test
    public void testThatGermanPostalAddressStreetWithHouseNumberIsGenerated() {

        GeneratorConfiguration de = new GeneratorConfiguration();
        FieldEnhancerPostalAddress f = new FieldEnhancerPostalAddress();
        de.addAdditionalField(FieldConstants.street, f);
        de.addAdditionalField(FieldConstants.houseNumber, f);

        Person person = new Person("Erika", "Mustermann", EnumGender.female);
        f.addFields(de, person, FieldConstants.street);
        f.addFields(de, person, FieldConstants.houseNumber);

        String street = f.randomStreet(de);
        assertThat(street).isNotNull();
        assertThat(street).isNotEmpty();
        String houseNumber = f.randomHouseNumber(de);
        assertThat(houseNumber).isNotNull();
        assertThat(houseNumber).isNotEmpty();
    }
}