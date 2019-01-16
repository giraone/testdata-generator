package com.giraone.testdata;

import com.giraone.testdata.generator.EnumLanguage;
import com.giraone.testdata.generator.Generator;
import com.giraone.testdata.output.PersonListWriter;
import com.giraone.testdata.output.PersonListWriterCsv;
import com.giraone.testdata.output.PersonListWriterJson;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

public class GeneratorMain {

    private int numberOfItems = 1;
    private int filesPerDirectory = -1;
    private int numberOfDirectories = -1;
    private PersonListWriter listWriter = new PersonListWriterJson();
    private EnumLanguage language = EnumLanguage.de;
    private File parentDirectory = new File(".");

    public static void main(String[] args) throws Exception {

        new GeneratorMain().run(args);
    }

    private void run(String[] args) throws Exception {

        Options options = new Options();
        options.addOption("h", "help", false, "print usage help");
        options.addOption("l", "language", true, "the language for which the test data is generated (either \"en\" or \"de\")");
        options.addOption("s", "serialize", true, "the serialization mode: either json or csv");
        options.addOption("i", "items", true, "the number of items, that should be produced in total or in a file");
        options.addOption("s", "filesPerDirectory", true, "the number of files per directory");
        options.addOption("s", "numberOfDirectories", true, "the number of directories");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.getOptionValue("help") != null) {
                usage(options);
                System.exit(1);
            }
            this.language = EnumLanguage.valueOf(cmd.getOptionValue("language", language.toString()));

            if ("csv".equals(cmd.getOptionValue("serialize", "json"))) {
                this.listWriter = new PersonListWriterCsv();
            }
            this.numberOfItems = Integer.parseInt(cmd.getOptionValue("items", "" + numberOfItems));
            this.filesPerDirectory = Integer.parseInt(cmd.getOptionValue("filesPerDirectory", "" + filesPerDirectory));
            this.numberOfDirectories = Integer.parseInt(cmd.getOptionValue("numberOfDirectories", "" + numberOfDirectories));

            this.run();

        } catch (ParseException e) {
            e.printStackTrace();
            usage(options);
        }
    }

    private void run() throws Exception {

        Generator generator = new Generator(this.language);

        if (this.filesPerDirectory > 0 || this.numberOfDirectories > 0) {
            this.runBlockwise(generator);
        } else {
            this.runOneList(generator, System.out);
        }
    }

    private void runOneList(Generator generator, PrintStream out) throws Exception {

        List<Person> personList = generator.randomPersons(0, this.numberOfItems);
        this.listWriter.write(personList, out);
    }

    private void runBlockwise(Generator generator) throws Exception {

        final String extension = this.listWriter instanceof PersonListWriterCsv ? ".csv" : ".json";
        for (int directoryIndex = 0; directoryIndex < this.numberOfDirectories; directoryIndex++) {
            final String directoryName = String.format("d-%08d", directoryIndex);
            final File directory = new File(this.parentDirectory, directoryName);
            for (int fileIndex = 0; fileIndex < this.filesPerDirectory; fileIndex++) {
                if (fileIndex == 0) {
                    directory.mkdirs();
                }
                final String fileName = String.format("f-%08d.%s", fileIndex, extension);
                final File file = new File(directory, fileName);
                try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
                    this.runOneList(generator, out);
                }
            }
        }
    }

    private void usage(Options options) {

        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar testdata-generator-1.0.jar", options);
    }
}
