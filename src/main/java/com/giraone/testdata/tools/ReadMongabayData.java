package com.giraone.testdata.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class was internally used to convert data from https://names.mongabay.com/most_common_surnames.htm
 */
public class ReadMongabayData {
    public static void main(String[] args) throws Exception {

        HashMap<String, Integer> m = new HashMap<>();
        File file = new File("x.txt");
        try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() == 0 || line.startsWith("#")) continue;
                String[] pieces = line.split("[,]");
                if (pieces.length == 2) {
                    String name = pieces[0];
                    name = name.substring(0,1) + name.substring(1).toLowerCase();
                    int count = Integer.parseInt(pieces[1]);
                    Integer c = m.get(name);
                    m.put(name, c == null ? count : c + count);
                }
            }
        }


        int limit = 10;
        List<Map.Entry<String, Integer>> ml = m.entrySet().stream()
                .filter(e -> e.getValue() >= limit)
                .sorted(Comparator.comparing(Map.Entry::getValue, Collections.reverseOrder()))
                .map(e -> {
                    e.setValue(e.getValue() / limit);
                    return e;
                })
                .collect(Collectors.toList());

        File dataDirectory = new File("src/main/resources/data");

        File outputMale = new File(dataDirectory, "surname-weighted-en.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(outputMale.toPath(), StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Integer> e : ml) {
                writer.write(e.getKey() + "," + e.getValue() + "\r\n");
            }
        }
    }
}
