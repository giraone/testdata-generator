package com.giraone.testdata.output;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.giraone.testdata.Person;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class PersonListWriterCsv implements PersonListWriter {

    protected static TypeReference<List<Person>> typeRef = new TypeReference<List<Person>>(){};

    @Override
    public void write(List<Person> personList, PrintStream out) throws IOException {

        ObjectMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .setUseHeader(true)
                .addColumn("index", CsvSchema.ColumnType.NUMBER)
                .addColumn("id")
                .addColumn("surname")
                .addColumn("givenName")
                .addColumn("gender", CsvSchema.ColumnType.NUMBER)
                .addColumn("dateOfBirth")
                .addColumn("postalCode")
                .addColumn("city")
                .addColumn("streetAddress")
                .addColumn("companyId")
                .addColumn("email")
                .addColumn("iban")
                .build();
        mapper.writerFor(typeRef)
                .with(schema)
                .writeValue(out, personList);
    }
}
