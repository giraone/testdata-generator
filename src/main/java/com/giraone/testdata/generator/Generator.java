package com.giraone.testdata.generator;

import com.giraone.testdata.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class Generator {

    private static final Logger log = LogManager.getLogger(Generator.class);

    private static final String DATA_PATH = "./src/main/resources/data";

    private static final Random RANDOM = new Random();
    private static LocalDate fromLocalDate = LocalDate.parse("1930-01-01");
    private static LocalDate toLocalDate = LocalDate.now().minusYears(16);
    private static int dayLimit = (int) DAYS.between(fromLocalDate, toLocalDate);

    private static final HashMap<String, ArrayList<String>> RANDOM_FROM_FILE = new HashMap<>();
    private static final HashMap<String, ArrayList<String>> RANDOM_FROM_WEIGHTED_FILE = new HashMap<>();
    private static final HashMap<String, HashMap<Integer, Integer>> WEIGHT_FROM_WEIGHTED_FILE = new HashMap<>();

    private EnumLanguage language;
    private boolean withIndex;
    private EnumIdType idType = EnumIdType.none;
    private Set<EnumField> additionalFields = new HashSet<>();

    //- Constructor and getter/setters ---------------------------------------------------------------------------------

    public Generator(EnumLanguage language) {
        this.language = language;
    }

    public EnumLanguage getLanguage() {
        return language;
    }

    public void setLanguage(EnumLanguage language) {
        this.language = language;
    }

    public boolean isWithIndex() {
        return withIndex;
    }

    public void setWithIndex(boolean withIndex) {
        this.withIndex = withIndex;
    }

    public EnumIdType getIdType() {
        return idType;
    }

    public void setIdType(EnumIdType idType) {
        this.idType = idType;
    }

    public Set<EnumField> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Set<EnumField> additionalFields) {
        this.additionalFields = additionalFields;
    }

    public void addAdditionalField(EnumField additionalField) {
        this.additionalFields.add(additionalField);
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

        if (this.withIndex && (index >= 0)) {
            person.setIndex(index);
        }

        if (this.idType == EnumIdType.sequence) {
            person.setId(Long.toUnsignedString(index));
        } else if (this.idType == EnumIdType.uuid) {
            person.setId(UUID.randomUUID().toString());
        }

        for (EnumField field : this.additionalFields) {
            final String value = randomDateOfBirthAsIsoString();
            person.setAdditionalField(field, value);
        }

        return person;
    }

    public Person randomPerson() {
        return randomPerson(this.withIndex ? System.currentTimeMillis() : -1);
    }

    public List<Person> randomPersons(int startIndex, int endIndex) {

        ArrayList<Person> ret = new ArrayList<>();
        for (int index = startIndex; index < endIndex; index++) {
            final Person person = randomPerson(index);
            ret.add(person);
        }
        return ret;
    }

    public EnumGender randomGender() {
        return RANDOM.nextBoolean() ? EnumGender.male : EnumGender.female;
    }

    public String randomDateOfBirthAsIsoString() {
        return DateTimeFormatter.BASIC_ISO_DATE.format(fromLocalDate.plusDays(RANDOM.nextInt(dayLimit)));
    }

    public String randomGivenName(EnumGender gender) {
        return randomFromWeightedFile(getFileForGivenName(gender));
    }

    public String randomSurname() {
        return randomFromWeightedFile(getFileForSurname());
    }

    //- PRIVATE --------------------------------------------------------------------------------------------------------

    private String randomFromFile(String file) {
        ArrayList<String> valueList = RANDOM_FROM_FILE.get(file);
        if (valueList == null) {
            valueList = new ArrayList<>();
            Path path = FileSystems.getDefault().getPath(DATA_PATH + "/" + file);
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
            log.debug("Reading data file \"" + file + "\": nr of entries = " + valueList.size());
        }
        return valueList.get(RANDOM.nextInt(valueList.size()));
    }

    private String randomFromWeightedFile(String file) {
        ArrayList<String> valueList = valueListFromWeightedFile(file);
        HashMap<Integer, Integer> weightMap = WEIGHT_FROM_WEIGHTED_FILE.get(file);
        return valueList.get(weightMap.get(RANDOM.nextInt(weightMap.size())));
    }

    private ArrayList<String> valueListFromWeightedFile(String file) {
        ArrayList<String> valueList = RANDOM_FROM_WEIGHTED_FILE.get(file);
        if (valueList == null) {
            Instant start = Instant.now();
            valueList = new ArrayList<>();
            HashMap<Integer, Integer> weightMap = new HashMap<>();
            Path path = FileSystems.getDefault().getPath(DATA_PATH + "/" + file);
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
                        log.warn("Invalid line in " + file + ": " + line);
                    }
                }
            } catch (IOException io) {
                throw new IllegalStateException(io);
            }
            RANDOM_FROM_WEIGHTED_FILE.put(file, valueList);
            WEIGHT_FROM_WEIGHTED_FILE.put(file, weightMap);

            Instant finish = Instant.now();
            long timeElapsed = Duration.between(start, finish).toMillis();
            log.debug("Reading weighted data file \"" + file + "\": nr of entries = " + valueList.size()
                    + ", time to read = " + timeElapsed + " milliseconds");
        }

        return valueList;
    }

    private String getFileForGivenName(EnumGender gender) {
        return "givenname-" + ((EnumGender.male == gender) ? "male" : "female") + "-weighted-" + language + ".txt";
    }

    private String getFileForSurname() {
        return "surname-weighted-" + language + ".txt";
    }

    private boolean isNotNullOrEmpty(String s) {
        return s != null && s.length() > 0;
    }
}
