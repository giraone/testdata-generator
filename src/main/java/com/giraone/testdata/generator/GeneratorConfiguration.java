package com.giraone.testdata.generator;

import com.giraone.testdata.fields.FieldEnhancer;
import com.giraone.testdata.fields.company.CompanyHierarchySpecification;
import com.giraone.testdata.output.PersonListWriter;
import com.giraone.testdata.output.PersonListWriterCsv;
import com.giraone.testdata.output.PersonListWriterJson;
import com.giraone.testdata.util.KeyValueReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneratorConfiguration {

    /** the language for which the test data is generated (either "en" or "de") */
    public EnumLanguage language = EnumLanguage.de;

    /** the country for which the test data is generated - a ISO 3166-Alpha-3 code - currently only DEU is supported */
    public String country = "DEU";

    /** create also a sequence number (index) for each created item */
    public boolean withIndex = false;
    /** if withIndex is used, this is the start index */
    public int startIndex = 0;

    /** type of additional person id: none, uuid, sequence */
    public EnumIdType idType = EnumIdType.none;

    /** set of additional fields - {@see FieldEnhancer} */
    public List<AdditionalField> additionalFields = new ArrayList<>();

    /**constant fields, that are added randomly */
    public List<FieldSpec> constantFields = new ArrayList<>();

    /** configuration for company IDs - {@see FieldEnhancerCompany} */
    public CompanyHierarchySpecification companySpec = CompanyHierarchySpecification.DEFAULT;

    /** the number of items, that should be produced in total or in a file */
    public int numberOfItems = 1;
    /** the number of files per directory */
    public int filesPerDirectory = -1;
    /** the number of directories to be created for splitting the output */
    public int numberOfDirectories = -1;

    /** the root directory into which the data is generated */
    public File rootDirectory = new File(".");

    /** an optional JSON alias file to map attribute names */
    public File aliasJsonFile = null;

    /** an optional JSON format file to format attribute values */
    public File formatJsonFile = null;

    /** use snake_case output */
    public boolean snakeCaseOutput = false;

    /** use CSV output */
    public boolean csvOutput = false;


    /** the writer class responsible for the output serialization: typically either json or csv */
    private PersonListWriter listWriter;

    private KeyValueReader aliasReader;
    private KeyValueReader formatReader;

    public KeyValueReader getAliasReader() {

        if (aliasJsonFile == null) {
            return null;
        }
        if (aliasReader == null) {
            aliasReader = new KeyValueReader();
            try {
                aliasReader.convertToJsonMap(aliasJsonFile);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return aliasReader;
    }

    public KeyValueReader getFormatReader() {

        if (formatJsonFile == null) {
            return null;
        }
        if (formatReader == null) {
            formatReader = new KeyValueReader();
            try {
                formatReader.convertToJsonMap(formatJsonFile);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return formatReader;
    }

    public void addAdditionalField(String name, FieldEnhancer fieldEnhancer) {

       this.additionalFields.add(new AdditionalField(name, fieldEnhancer));
    }

    public boolean containsAdditionalField(String name) {

        for (AdditionalField field: additionalFields) {
            if (name.equals(field.getName())) {
                return true;
            }
        }
        return false;
    }

    public void initializeWriter() {

        if (this.csvOutput) {
            this.listWriter = new PersonListWriterCsv();
        } else {
            this.listWriter = new PersonListWriterJson();
        }
    }

    public PersonListWriter getListWriter() {
        return listWriter;
    }
}
