package com.giraone.testdata.generator;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum EnumGender
{
    unknown("u"), male("m"), female("f");

    private static Map<String, EnumGender> stringToEnum = new HashMap<>();

    static
    {
        for (EnumGender e : EnumGender.values())
        {
            EnumGender.stringToEnum.put(e.toString(), e);
        }
    }

    // -------------------------------------------------------------------------------------------------

    private final String str;

    EnumGender(String str)
    {
        this.str = str;
    }

    public static EnumGender fromString(String strValue)
    {
        EnumGender ret = EnumGender.stringToEnum.get(strValue);
        return ret == null ? EnumGender.unknown : ret;
    }

    @JsonValue
    @Override
    public String toString()
    {
        return str;
    }
}