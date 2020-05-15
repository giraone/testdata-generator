package com.giraone.testdata.fields;

import java.util.Random;

/**
 * Calculate german ssn derived from birthName, dateOfBirth, gender
 */
public class SsnUs {

    private static final Random RANDOM = new Random();

    public static String build() {

        return String.format("%03d-%02d-%04d", random(132,921), random(12, 83), random(1423, 9211));
    }

    private static int random(int min, int max) {

        return min + RANDOM.nextInt(max - min);
    }
}