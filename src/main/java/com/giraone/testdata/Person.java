package com.giraone.testdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.giraone.testdata.generator.EnumField;
import com.giraone.testdata.generator.EnumGender;

import java.util.HashMap;
import java.util.Map;

public class Person {

    String givenName;
    String surname;
    EnumGender gender;
    Long index;
    String id;
    Map<EnumField,String> additionalFields;

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

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public EnumGender getGender() {
        return gender;
    }

    public void setGender(EnumGender gender) {
        this.gender = gender;
    }

    public Long getIndex() {
        return index;
    }

    public void setIndex(Long index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map<EnumField, String> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalField(EnumField field, String value) {
        if (this.additionalFields == null) {
            this.additionalFields = new HashMap<>();
        }
        this.additionalFields.put(field, value);
    }

    public String getAdditionalField(EnumField field) {
        return this.additionalFields != null ? this.additionalFields.get(field) : null;
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
