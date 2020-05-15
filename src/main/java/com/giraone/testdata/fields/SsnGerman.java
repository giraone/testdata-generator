package com.giraone.testdata.fields;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Calculate german ssn derived from birthName, dateOfBirth, gender
 */
public class SsnGerman {

    private static final Random RANDOM = new Random();
    private static final Map<Character,Character> MAP_CHAR = new HashMap<>();
    private static final int[] CHECKSUM_WEIGHT_DE = new int[]{ 2, 1, 2, 5, 7, 1, 2, 1, 2, 1, 2, 1 };
    public static final String[] BEREICHSNUMMER = new String[]{
        "02", "03", "04", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
        "21", "23", "24", "25", "26", "28", "29", "38", "39", "40", "42", "43", "44", "45", "46", "47",
        "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63",
        "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79",
        "80", "81", "82", "89"};
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("ddMMyy");

    static {
        MAP_CHAR.put('Ä', 'A');
        MAP_CHAR.put('Ö', 'O');
        MAP_CHAR.put('Ü', 'U');
    }

    // No instances
    private SsnGerman() {
    }

    public static String build(String birthName, TemporalAccessor dateOfBirth, boolean male) {

        final String bereich = BEREICHSNUMMER[RANDOM.nextInt(BEREICHSNUMMER.length)];
        return build(bereich, birthName, dateOfBirth, male);
    }

    public static String build(String bereich, String birthName, TemporalAccessor dateOfBirth, boolean male) {

        final String serienNummer = String.format("%02d", male ? RANDOM.nextInt(50) : 50 + RANDOM.nextInt(50));
        return build(bereich, birthName, dateOfBirth, serienNummer);
    }

    public static String build(String bereich, String birthName, TemporalAccessor dateOfBirth, String serienNummer) {

        char initial = birthName.substring(0, 1).toUpperCase().charAt(0);
        if (MAP_CHAR.get(initial) != null) {
            initial = MAP_CHAR.get(initial);
        }
        final String dateOfBirthString = DATE_FORMATTER.format(dateOfBirth);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bereich).append(dateOfBirthString).append(initial).append(serienNummer);
        final String ssnWithoutChecksum = stringBuilder.toString();
        final int checkSum = calculateChecksumOfGermanSsn(ssnWithoutChecksum);
        stringBuilder.append(checkSum);
        return stringBuilder.toString();
    }

    public static int calculateChecksumOfGermanSsn(String ssn) {

        // See http://www.pruefziffernberechnung.de/V/VSNR-DE.shtml

        char[] charArray = ssn.toCharArray();
        int[] intArray = new int[charArray.length + 1];
        int j = 0;
        for (final char c : charArray) {
            if (c >= 'A') {
                final int intValue = c - 'A' + 1;
                intArray[j++] = intValue / 10;
                intArray[j++] = intValue % 10;
            } else {
                intArray[j++] = c - '0';
            }
        }

        int checksum = 0;
        for (int i = 0; i < intArray.length; i++) {
            final int weight = CHECKSUM_WEIGHT_DE[i] * intArray[i];
            // System.err.println(intArray[i] + " " + weight);
            checksum += checksum(weight);
        }
        return checksum % 10;
    }

    static int checksum(int number) {
        int checksum = 0;
        while (0 != number) {
            checksum += (number % 10);
            number = number / 10;
        }
        return checksum;
    }
}