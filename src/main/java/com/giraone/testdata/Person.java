package com.giraone.testdata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.giraone.testdata.fields.FieldConstants;
import com.giraone.testdata.generator.EnumGender;
import com.giraone.testdata.generator.GeneratorConfiguration;
import com.google.common.base.CaseFormat;

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

    @SuppressWarnings("unchecked")
    public void setAdditionalField(GeneratorConfiguration configuration, String field, Object value) {

        // System.err.println(" -> " + field + " = " + value);
        if (this.additionalFields == null) {
            this.additionalFields = new HashMap<>();
        }
        int i;
        if ((i = field.indexOf('.')) > 0) {
            String prefix = field.substring(0, i);
            String suffix = field.substring(i + 1);
            Map<String, Object> subObject;
            if ((subObject = (Map<String, Object>) this.additionalFields.get(prefix)) == null) {
                subObject = new HashMap<>();
            }
            subObject.put(mapFieldName(configuration, prefix, suffix), mapValue(configuration, field, value));
            this.additionalFields.put(mapFieldName(configuration, prefix), subObject);
        } else {
            this.additionalFields.put(mapFieldName(configuration, field), mapValue(configuration, field, value));
        }
    }

    @SuppressWarnings("unchecked")
    public Object getAdditionalField(GeneratorConfiguration configuration, String fieldName) {

        if (configuration.getAliasReader() != null) {
            fieldName = configuration.getAliasReader().getValue(fieldName, fieldName);
        }

        int i;
        if ((i = fieldName.indexOf('.')) > 0) {
            String prefix = fieldName.substring(0, i);
            String suffix = fieldName.substring(0, i);
            Map<String, Object> subObject = (Map<String, Object>) this.additionalFields.get(prefix);
            return subObject != null ? subObject.get(suffix) : null;
        } else {
            return this.additionalFields != null ? this.additionalFields.get(fieldName) : null;
        }
    }

    public void putBasicFields(GeneratorConfiguration configuration) {
        this.setField(configuration, FieldConstants.surname, surname);
        this.setField(configuration, FieldConstants.givenName, givenName);
        this.setField(configuration, FieldConstants.gender, gender);
        this.setField(configuration, FieldConstants.id, id);
        this.setField(configuration, FieldConstants.index, index);
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

    //------------------------------------------------------------------------------------------------------------------

    private void setField(GeneratorConfiguration configuration, String field, Object value) {

        this.setAdditionalField(configuration, mapFieldName(configuration, field),
            mapValue(configuration, field, value));
    }

    private String mapFieldName(GeneratorConfiguration configuration, String fieldName) {

        if (configuration.getAliasReader() != null) {
            fieldName = configuration.getAliasReader().getValue(fieldName, fieldName);
        }
        if (configuration.snakeCaseOutput) {
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
        } else {
            return fieldName;
        }
    }

    private String mapFieldName(GeneratorConfiguration configuration, String prefix, String suffix) {

        if (configuration.getAliasReader() != null) {
            final String fieldName = prefix + '.' + suffix;
            final String aliasName = configuration.getAliasReader().getValue(fieldName, fieldName);
            int i;
            if ((i = aliasName.indexOf('.')) ==  -1) {
                throw new IllegalArgumentException("Alias " + aliasName + " must contain .");
            }
            suffix = aliasName.substring(i + 1);
        }
        if (configuration.snakeCaseOutput) {
            return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, suffix);
        } else {
            return suffix;
        }
    }

    private Object mapValue(GeneratorConfiguration configuration, String field, Object value) {

        if (configuration.getFormatReader() == null) {
           return value;
        }
        final String format = configuration.getFormatReader().getValue(field);

        if (format == null) {
            return value;
        }
        return String.format(format, Integer.parseInt(value.toString()));
    }
}
