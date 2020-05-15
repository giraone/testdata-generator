package com.giraone.testdata.output;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.giraone.testdata.Person;
import com.giraone.testdata.fields.FieldConstants;

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
                .addColumn(FieldConstants.index, CsvSchema.ColumnType.NUMBER)
                .addColumn(FieldConstants.id)
                .addColumn(FieldConstants.surname)
                .addColumn(FieldConstants.givenName)
                .addColumn(FieldConstants.gender, CsvSchema.ColumnType.NUMBER)
                .addColumn(FieldConstants.dateOfBirth)
                .addColumn(FieldConstants.postalCode)
                .addColumn(FieldConstants.city)
                .addColumn(FieldConstants.streetAddress)
                .addColumn(FieldConstants.street)
                .addColumn(FieldConstants.houseNumber)
                .addColumn(FieldConstants.companyId)
                .addColumn(FieldConstants.email)
                .addColumn(FieldConstants.iban)
                .addColumn(FieldConstants.phoneNumber)
                .build();
        mapper.writerFor(typeRef)
                .with(schema)
                .writeValue(out, personList);
    }
}
