package com.giraone.testdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.giraone.testdata.fields.FieldConstants;
import com.giraone.testdata.generator.EnumGender;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.util.HashMap;
import java.util.Map;

public class Person {

    Long index;
    String id;
    String givenName;
    String surname;
    EnumGender gender;
    Map<String, Object> additionalFields;

    public Person(long index, String givenName, String surname, EnumGender gender) {
        this.index = index;
        this.givenName = givenName;
        this.surname = surname;
        this.gender = gender;
    }

    public Person(String givenName, String surname, EnumGender gender) {
        this.givenName = givenName;
        this.surname = surname;
        this.gender = gender;
    }

    @JsonIgnore
    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getGivenName() {
        return givenName;
    }

    @JsonIgnore
    public String getSurname() {
        return surname;
    }

    @JsonIgnore
    public EnumGender getGender() {
        return gender;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalField(String field, Object value) {
        if (this.additionalFields == null) {
            this.additionalFields = new HashMap<>();
        }
        this.additionalFields.put(field, value);
    }

    public Object getAdditionalField(String field) {
        return this.additionalFields != null ? this.additionalFields.get(field) : null;
    }

    public void putBasicFields(GeneratorConfiguration configuration) {
        this.setField(configuration, FieldConstants.surname, surname);
        this.setField(configuration, FieldConstants.givenName, givenName);
        this.setField(configuration, FieldConstants.gender, gender);
        this.setField(configuration, FieldConstants.id, id);
        this.setField(configuration, FieldConstants.index, index);
    }

    public void setField(GeneratorConfiguration configuration, String field, Object value) {

        if (configuration.getAliasReader() != null) {
            this.setAdditionalField(configuration.getAliasReader().getFieldName(field), value);
        } else {
            this.setAdditionalField(field, value);
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "givenName='" + givenName + '\'' +
                ", surname='" + surname + '\'' +
                ", gender=" + gender +
                ", index=" + index +
                ", id='" + id + '\'' +
                '}';
    }
}
