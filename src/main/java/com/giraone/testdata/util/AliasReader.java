package com.giraone.testdata.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AliasReader {

    private final ObjectMapper mapper = new ObjectMapper();
    private final TypeReference<Map<String, String>> mapTypeRef
        = new TypeReference<Map<String, String>>() {
    };

    private Map<String, String> aliasMapping = new HashMap<>();

    public int convertToJsonMap(File jsonFile)
        throws IOException {

        aliasMapping = mapper.readValue(jsonFile, mapTypeRef);
        return aliasMapping.size();
    }

    public String getFieldName(String name) {

        final String alias = aliasMapping.get(name);
        if (alias != null) {
            return alias;
        } else {
            return name;
        }
    }
}

