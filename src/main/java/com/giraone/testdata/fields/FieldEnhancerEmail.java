package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.generator.GeneratorConfiguration;
import com.giraone.testdata.util.NameNormalizeService;

import java.util.Random;

/**
 * This class adds an "email" string value to the person, which ist derived from given name and surname.
 */
@SuppressWarnings("unused")
public class FieldEnhancerEmail implements FieldEnhancer {

    private static final Random RANDOM = new Random();

    NameNormalizeService nameNormalizeService = new NameNormalizeService();

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {


        final String givenName = nameNormalizeService.normalize(person.getGivenName());
        final String surname = nameNormalizeService.normalize(person.getSurname());
        final String index = person.getIndex() != null ? ("." + person.getIndex()) : "";

        if (RANDOM.nextInt(5) == 0) {
            setAdditionalField(configuration, person, FieldConstants.email, givenName + index + "@" + surname + ".com");
        } else {
            final String domain = randomMailDomain(configuration);
            setAdditionalField(configuration, person, FieldConstants.email, givenName + "." + surname + index + "@" + domain);
        }
    }

    protected String randomMailDomain(GeneratorConfiguration configuration) {

        return Generator.randomFromWeightedFile("mail-domain-weighted-" + configuration.language + ".txt");
    }
}