package com.giraone.testdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.function.BiFunction;
import java.util.function.Function;

public class GeneratorMain {

    private static ObjectMapper objectMapper = new ObjectMapper();

    boolean useBuiltInDataSets = true;
    int numberOfItems = 1;
    boolean asCsv = false;
    EnumLanguage language = EnumLanguage.de;

    public static void main(String[] args) throws Exception {

        new GeneratorMain().run(args);
    }

    protected void run(String[] args) throws Exception {

        Options options = new Options();
        options.addOption("h", "help", false, "print usage help");
        options.addOption("l", "language", true, "the language for which the test data is generated (either \"en\" or \"de\")");
        options.addOption("i", "items", true, "the number of items, that should be produced");
        options.addOption("s", "serialize", true, "the serialization mode: either json or csv");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.getOptionValue("help") != null) {
               usage(options);
               System.exit(1);
            }
            this.language = EnumLanguage.valueOf(cmd.getOptionValue("language", language.toString()));
            this.numberOfItems = Integer.parseInt(cmd.getOptionValue("items", "" + numberOfItems));
            this.asCsv = "csv".equals(cmd.getOptionValue("serialize", "json"));

            //BiFunction<Person, PrintStream, Void> f = GeneratorMain::writeJson;

            Generator generator = new Generator(this.language);
            for (int i = 0; i < this.numberOfItems; i++) {
                Person person = generator.randomPerson();
                writeJson(person, System.out);
                //f.apply(person, System.out);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            usage(options);
        }
    }

    protected static void writeJson(Person person, PrintStream out)  {
        try {
            objectMapper.writeValue(out, person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void usage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar testdata-generator-1.0.jar", options);
    }
}
