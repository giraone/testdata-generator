package com.giraone.testdata.util;

import java.util.HashMap;

public class NameNormalizeService {

    public NameNormalizeService() {
    }

    /**
     * Normalize a name by using trim, lowercase and umlaut replacement
     *
     * @param input The input string
     * @return the normalized input string or null, if the input is ull or contains only whitespaces
     */
    public String normalize(String input) {

        if (input == null) {
            return null;
        }
        input = input.trim().toLowerCase();
        input = replaceUmlauts(input);
        if (input.length() > 0) {
            return input;
        } else {
            return null;
        }
    }

    public String replaceUmlauts(String in) {
        if (in == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder();
        String r;
        for (int i = 0; i < in.length(); i++) {
            final char c = in.charAt(i);
            if ((r = UMLAUT_REPLACEMENTS.get(c)) != null)
                ret.append(r);
            else
                ret.append(c);
        }
        return ret.toString();
    }

    private static final HashMap<Character, String> UMLAUT_REPLACEMENTS = new HashMap<>();

    static {
        UMLAUT_REPLACEMENTS.put('Ä', "ae");
        UMLAUT_REPLACEMENTS.put('ä', "ae");
        UMLAUT_REPLACEMENTS.put('Ö', "oe");
        UMLAUT_REPLACEMENTS.put('ö', "oe");
        UMLAUT_REPLACEMENTS.put('Ü', "ue");
        UMLAUT_REPLACEMENTS.put('ü', "ue");
        UMLAUT_REPLACEMENTS.put('ß', "ss");
        UMLAUT_REPLACEMENTS.put('é', "e");
        UMLAUT_REPLACEMENTS.put('è', "e");
        UMLAUT_REPLACEMENTS.put('ê', "e");
        UMLAUT_REPLACEMENTS.put('á', "a");
        UMLAUT_REPLACEMENTS.put('à', "a");
        UMLAUT_REPLACEMENTS.put('â', "a");
        UMLAUT_REPLACEMENTS.put('ç', "c");
    }
}
