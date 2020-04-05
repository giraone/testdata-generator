package com.giraone.testdata.generator;

import com.giraone.testdata.fields.FieldEnhancer;
import com.giraone.testdata.fields.company.CompanyHierarchySpecification;
import com.giraone.testdata.output.PersonListWriter;
import com.giraone.testdata.output.PersonListWriterJson;
import com.giraone.testdata.util.AliasReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, FieldEnhancer> additionalFields = new HashMap<>();

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

    /** the writer class responsible for the output serialization: typically either json or csv */
    public PersonListWriter listWriter = new PersonListWriterJson();

    private AliasReader aliasReader;

    public AliasReader getAliasReader() {

        if (aliasJsonFile == null) {
            return null;
        }
        if (aliasReader == null) {
            aliasReader = new AliasReader();
            try {
                aliasReader.convertToJsonMap(aliasJsonFile);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return aliasReader;
    }
}
