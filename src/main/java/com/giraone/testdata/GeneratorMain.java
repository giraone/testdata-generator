package com.giraone.testdata;

import com.giraone.testdata.generator.EnumField;
import com.giraone.testdata.generator.EnumIdType;
import com.giraone.testdata.generator.EnumLanguage;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.output.PersonListWriter;
import com.giraone.testdata.output.PersonListWriterCsv;
import com.giraone.testdata.output.PersonListWriterJson;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeneratorMain {

    private boolean withIndex = false;
    private int numberOfItems = 1;
    private int filesPerDirectory = -1;
    private int numberOfDirectories = -1;
    private PersonListWriter listWriter = new PersonListWriterJson();
    private EnumLanguage language = EnumLanguage.de;
    private EnumIdType personIdType = EnumIdType.none;
    private Set<EnumField> fields = new HashSet<>();
    private File parentDirectory = new File(".");

    public static void main(String[] args) throws Exception {

        new GeneratorMain().run(args);
    }

    private void run(String[] args) throws Exception {

        Options options = new Options();
        options.addOption("h", "help", false, "print usage help");
        options.addOption("w", "withIndex", false, "create also a sequence number (index)");
        options.addOption("l", "language", true, "the language for which the test data is generated (either \"en\" or \"de\")");
        options.addOption("s", "serialize", true, "the serialization mode: either json or csv");
        options.addOption("p", "personId", true, "type of additional person id: none, uuid, sequence");
        options.addOption("a", "additionalFields", true, "comma separated list of additional fields");
        options.addOption("i", "items", true, "the number of items, that should be produced in total or in a file");
        options.addOption("f", "filesPerDirectory", true, "the number of files per directory");
        options.addOption("d", "numberOfDirectories", true, "the number of directories");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                usage(options);
                System.exit(1);
            }
            language = EnumLanguage.valueOf(cmd.getOptionValue("language", language.toString()));
            withIndex = cmd.hasOption("withIndex");
            if ("csv".equals(cmd.getOptionValue("serialize", "json").toLowerCase())) {
                listWriter = new PersonListWriterCsv();
            }
            personIdType = EnumIdType.valueOf(cmd.getOptionValue("personId", personIdType.toString()));
            parseFields(cmd.getOptionValue("additionalFields", ""), fields);
            numberOfItems = Integer.parseInt(cmd.getOptionValue("items", "" + numberOfItems));
            filesPerDirectory = Integer.parseInt(cmd.getOptionValue("filesPerDirectory", "" + filesPerDirectory));
            numberOfDirectories = Integer.parseInt(cmd.getOptionValue("numberOfDirectories", "" + numberOfDirectories));

            run();

        } catch (ParseException e) {
            e.printStackTrace();
            usage(options);
        }
    }

    private void run() throws Exception {

        Generator generator = new Generator(language);
        generator.setWithIndex(withIndex);
        generator.setIdType(personIdType);
        generator.setAdditionalFields(fields);

        if (filesPerDirectory > 0 || numberOfDirectories > 0) {
            runBlockwise(generator);
        } else {
            runOneList(generator, System.out);
        }
    }

    private void runOneList(Generator generator, PrintStream out) throws Exception {

        List<Person> personList = generator.randomPersons(0, numberOfItems);
        listWriter.write(personList, out);
    }

    private void runBlockwise(Generator generator) throws Exception {

        final String extension = listWriter instanceof PersonListWriterCsv ? ".csv" : ".json";
        for (int directoryIndex = 0; directoryIndex < numberOfDirectories; directoryIndex++) {
            final String directoryName = String.format("d-%08d", directoryIndex);
            final File directory = new File(parentDirectory, directoryName);
            for (int fileIndex = 0; fileIndex < filesPerDirectory; fileIndex++) {
                if (fileIndex == 0) {
                    directory.mkdirs();
                }
                final String fileName = String.format("f-%08d.%s", fileIndex, extension);
                final File file = new File(directory, fileName);
                try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
                    runOneList(generator, out);
                }
            }
        }
    }

    private void parseFields(String fieldCommaList, Set<EnumField> result) {

        if (fieldCommaList.trim().length() > 0) { // TODO
            result.add(EnumField.dateOfBirth);
        }
    }

    private void usage(Options options) {

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar testdata-generator-1.0.jar", options);
    }
}
