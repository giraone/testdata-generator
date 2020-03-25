package com.giraone.testdata.generator;

import com.giraone.testdata.Person;
import com.giraone.testdata.fields.FieldEnhancer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Generator {

    private static final Logger log = LogManager.getLogger(Generator.class);

    // TODO: 2 modes: read from resources and read from local directory
    private static final String DATA_PATH = "./src/main/resources/data";

    private static final Random RANDOM = new Random();

    private static final Map<String, List<String>> RANDOM_FROM_FILE = new HashMap<>();
    private static final Map<String, List<String>> RANDOM_FROM_WEIGHTED_FILE = new HashMap<>();
    private static final Map<String, Map<Integer, Integer>> WEIGHT_FROM_WEIGHTED_FILE = new HashMap<>();

    //- members --------------------------------------------------------------------------------------------------------

    private GeneratorConfiguration configuration;

    //- Constructor and getter/setters ---------------------------------------------------------------------------------

    public Generator(GeneratorConfiguration configuration) {
        this.configuration = configuration;
    }

    public GeneratorConfiguration getConfiguration() {
        return configuration;
    }

    //- PUBLIC ---------------------------------------------------------------------------------------------------------

    public int getNumberOfEntriesGivenName(EnumGender gender) {
        return valueListFromWeightedFile(getFileForGivenName(gender)).size();

    }

    public int getNumberOfEntriesSurname() {
        return valueListFromWeightedFile(getFileForSurname()).size();

    }

    public Person randomPerson(long index) {

        final EnumGender gender = randomGender();
        final Person person = new Person(randomGivenName(gender), randomSurname(), gender);

        if (configuration.withIndex && (index >= 0)) {
            person.setIndex(index);
        }

        person.putBasicFields(configuration);

        if (configuration.idType == EnumIdType.sequence) {
            person.setId(Long.toUnsignedString(index));
        } else if (configuration.idType == EnumIdType.uuid) {
            person.setId(UUID.randomUUID().toString());
        }

        for (Map.Entry<String, FieldEnhancer> field : configuration.additionalFields.entrySet()) {
            field.getValue().addFields(configuration, person, field.getKey());
        }

        return person;
    }

    public Person randomPerson() {
        return randomPerson(configuration.withIndex ? System.currentTimeMillis() : -1);
    }

    public List<Person> randomPersons(int startIndex, int endIndex) {

        final ArrayList<Person> ret = new ArrayList<>();
        for (int index = startIndex; index < endIndex; index++) {
            final Person person = randomPerson(index);
            ret.add(person);
        }
        return ret;
    }

    public EnumGender randomGender() {
        return RANDOM.nextBoolean() ? EnumGender.male : EnumGender.female;
    }

    public String randomGivenName(EnumGender gender) {
        return randomFromWeightedFile(getFileForGivenName(gender));
    }

    public String randomSurname() {
        return randomFromWeightedFile(getFileForSurname());
    }

    //- STATIC PUBLIC --------------------------------------------------------------------------------------------------

    public static String randomFromFile(String file) {
        List<String> valueList = RANDOM_FROM_FILE.get(file);
        if (valueList == null) {
            valueList = new ArrayList<>();
            Path path = FileSystems.getDefault().getPath(DATA_PATH + File.separator + file);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().length() == 0 || line.startsWith("#")) continue;
                    valueList.add(line);
                }
            } catch (IOException io) {
                throw new IllegalStateException(io);
            }
            RANDOM_FROM_FILE.put(file, valueList);
            log.debug("Reading data file \"{}\": nr of entries = {}", file, valueList.size());
        }
        return valueList.get(RANDOM.nextInt(valueList.size()));
    }

    public static String randomFromWeightedFile(String file) {
        List<String> valueList = valueListFromWeightedFile(file);
        Map<Integer, Integer> weightMap = WEIGHT_FROM_WEIGHTED_FILE.get(file);
        return valueList.get(weightMap.get(RANDOM.nextInt(weightMap.size())));
    }

    public static List<String> valueListFromWeightedFile(String file) {
        List<String> valueList = RANDOM_FROM_WEIGHTED_FILE.get(file);
        if (valueList == null) {
            Instant start = Instant.now();
            valueList = new ArrayList<>();
            Map<Integer, Integer> weightMap = new HashMap<>();
            Path path = FileSystems.getDefault().getPath(DATA_PATH + File.separator + file);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().length() == 0 || line.startsWith("#")) continue;
                    String[] pieces = line.split("[,]");
                    if (pieces.length == 2 && isNotNullOrEmpty(pieces[0]) && isNotNullOrEmpty(pieces[1])) {
                        int valuePos = valueList.size();
                        int weight = Integer.parseInt(pieces[1]);
                        valueList.add(pieces[0]);
                        for (int i = 0; i < weight; i++) {
                            weightMap.put(weightMap.size(), valuePos);
                        }
                    } else {
                        log.warn("Invalid line in {}: {}", file, line);
                    }
                }
            } catch (IOException io) {
                throw new IllegalStateException(io);
            }
            RANDOM_FROM_WEIGHTED_FILE.put(file, valueList);
            WEIGHT_FROM_WEIGHTED_FILE.put(file, weightMap);

            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            log.debug("Reading weighted data file \"{}\": nr of entries = {}, time to read = {} milliseconds",
                file, valueList.size(), timeElapsed);
        }

        return valueList;
    }

    //- PRIVATE --------------------------------------------------------------------------------------------------------

    private String getFileForGivenName(EnumGender gender) {
        return "givenname-" + ((EnumGender.male == gender) ? "male" : "female") + "-weighted-" + configuration.language + ".txt";
    }

    private String getFileForSurname() {
        return "surname-weighted-" + configuration.language + ".txt";
    }

    private static boolean isNotNullOrEmpty(String s) {
        return s != null && s.length() > 0;
    }
}
