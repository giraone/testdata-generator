package com.giraone.testdata.generator;

import com.giraone.testdata.Person;
import org.apache.logging.log4j.LogManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Generator {

    private static final org.apache.logging.log4j.Logger log = LogManager.getLogger(Generator.class);

    static final String DATA_PATH = "./src/main/resources/data";

    static final Random RANDOM = new Random();

    static final HashMap<String, ArrayList<String>> RANDOM_FROM_FILE = new HashMap<>();
    static final HashMap<String, ArrayList<String>> RANDOM_FROM_WEIGHTED_FILE = new HashMap<>();
    static final HashMap<String, HashMap<Integer, Integer>> WEIGHT_FROM_WEIGHTED_FILE = new HashMap<>();

    public static Random random() {
        return RANDOM;
    }

    private EnumLanguage language;
    private boolean withIndex;
    private EnumIdType idType = EnumIdType.none;

    public Generator(EnumLanguage language) {
        this.language = language;
    }

    public void setWithIndex(boolean withIndex) {
        this.withIndex = withIndex;
    }

    public void setIdType(EnumIdType idType) {
        this.idType = idType;
    }

    public int getNumberOfEntriesGivenName(EnumGender gender) {
        return valueListFromWeightedFile(getFileForGivenName(gender)).size();
    }

    public int getNumberOfEntriesSurname() {
        return valueListFromWeightedFile(getFileForSurname()).size();
    }

    public Person randomPerson() {
        final EnumGender gender = randomGender();
        return new Person(randomGivenName(gender), randomSurname(), gender);
    }

    public List<Person> randomPersons(int startIndex, int endIndex) {
        final EnumGender gender = randomGender();
        ArrayList<Person> ret = new ArrayList<>();
        for (int index = startIndex; index < endIndex; index++)
        {
            final Person person = new Person(randomGivenName(gender), randomSurname(), gender);
            ret.add(person);
        }
        return ret;
    }

    public EnumGender randomGender() {
        return RANDOM.nextBoolean() ? EnumGender.male : EnumGender.female;
    }

    public Calendar randomDateOfBirth() {
        Calendar d = GregorianCalendar.getInstance();
        d.set(RANDOM.nextInt(50) + 1935, RANDOM.nextInt(12), RANDOM.nextInt(29));
        return d;
    }

    public String randomGivenName(EnumGender gender) {
        return randomFromWeightedFile(getFileForGivenName(gender));
    }

    public String randomSurname() {
        return randomFromWeightedFile(getFileForSurname());
    }

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
