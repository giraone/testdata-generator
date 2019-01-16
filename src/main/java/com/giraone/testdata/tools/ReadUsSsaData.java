package com.giraone.testdata.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class was internally used to convert data from https://www.ssa.gov/OACT/babynames/names.zip
 * to the built-in data files for US given names. The data from ssa.gov is basically "baby name per year".
 * My collection was built by using the data of yob1900.txt to yob2017.txt. The class will
 * <ul>
 *     <li>split the file into male and female files</li>
 *     <li>sum up up the total occurrences for 117 years</li>
 *     <li>apply a filter to reduce the total number of names - see limit setting in the code</li>
 * </ul>
 */
public class ReadUsSsaData {
    public static void main(String[] args) throws Exception {

        HashMap<String, Integer> m = new HashMap<>();
        HashMap<String, Integer> f = new HashMap<>();

        File[] files = (new File("ssa.gov/names")).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.endsWith(".txt");
            }
        });
        if (files == null) return;

        for (File file : files) {
            try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().length() == 0 || line.startsWith("#")) continue;
                    String[] pieces = line.split("[,]");
                    if (pieces.length == 3) {
                        String name = pieces[0];
                        String gender = pieces[1];
                        int count = Integer.parseInt(pieces[2]);
                        if ("F".equals(gender)) {
                            Integer c = f.get(name);
                            f.put(name, c == null ? count : c + count);
                        } else {
                            Integer c = m.get(name);
                            m.put(name, c == null ? count : c + count);
                        }
                    }
                }
            }
        }

        int limit = 500;
        List<Map.Entry<String, Integer>> ml = m.entrySet().stream()
                .filter(e -> e.getValue() >= limit)
                .sorted(Comparator.comparing(Map.Entry::getValue, Collections.reverseOrder()))
                .map(e -> { e.setValue(e.getValue() / limit); return e; })
                .collect(Collectors.toList());

        List<Map.Entry<String, Integer>> fl = f.entrySet().stream()
                .filter(e -> e.getValue() >= limit)
                .sorted(Comparator.comparing(Map.Entry::getValue, Collections.reverseOrder()))
                .map(e -> { e.setValue(e.getValue() / limit); return e; })
                .collect(Collectors.toList());

        File dataDirectory = new File("src/main/resources/data");

        File outputMale = new File(dataDirectory, "givenname-male-weighted-en.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(outputMale.toPath(), StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Integer> e : ml) {
                writer.write(e.getKey() + "," + e.getValue() + "\r\n");
            }
        }

        File outputFemale = new File(dataDirectory, "givenname-female-weighted-en.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(outputFemale.toPath(), StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Integer> e : fl) {
                writer.write(e.getKey() + "," + e.getValue() + "\r\n");
            }
        }
    }
}
