package com.giraone.testdata;

import java.util.HashMap;
import java.util.Map;

public enum EnumGender
{
    unknown("unknown"), male("male"), female("female");

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

    private EnumGender(String str)
    {
        this.str = str;
    }

    public static EnumGender fromString(String strValue)
    {
        EnumGender ret = EnumGender.stringToEnum.get(strValue);
        return ret == null ? EnumGender.unknown : ret;
    }

    @Override
    public String toString()
    {
        return this.str;
    }
}