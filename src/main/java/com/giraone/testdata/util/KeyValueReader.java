package com.giraone.testdata.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KeyValueReader {

    private final ObjectMapper mapper = new ObjectMapper();
    private final TypeReference<Map<String, String>> mapTypeRef
        = new TypeReference<Map<String, String>>() {
    };

    private Map<String, String> mapping = new HashMap<>();

    public int convertToJsonMap(File jsonFile)
        throws IOException {

        mapping = mapper.readValue(jsonFile, mapTypeRef);
        return mapping.size();
    }

    public String getValue(String name) {

        return mapping.get(name);
    }

    public String getValue(String name, String defaultValue) {

        final String value = mapping.get(name);
        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }
}

