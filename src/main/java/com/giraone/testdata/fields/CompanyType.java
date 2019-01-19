package com.giraone.testdata.fields;

import java.util.HashMap;

class CompanyType {

    public enum CompanySizeType {
        small, medium, large
    }

    private static final HashMap<CompanySizeType, CompanyType> COMPANY_SIZE_TYPE_MAP = new HashMap<>();

    static {
        COMPANY_SIZE_TYPE_MAP.put(CompanySizeType.small, new CompanyType(2,20));
        COMPANY_SIZE_TYPE_MAP.put(CompanySizeType.medium, new CompanyType(20,500));
        COMPANY_SIZE_TYPE_MAP.put(CompanySizeType.large, new CompanyType(500,10000));
    }

    private int minimalNumberOfEmployees;
    private int maximalNumberOfEmployees;

    public CompanyType(int minimalNumberOfEmployees, int maximalNumberOfEmployees) {
        this.minimalNumberOfEmployees = minimalNumberOfEmployees;
        this.maximalNumberOfEmployees = maximalNumberOfEmployees;
    }

    public int getMinimalNumberOfEmployees() {
        return minimalNumberOfEmployees;
    }

    public int getMaximalNumberOfEmployees() {
        return maximalNumberOfEmployees;
    }

    public static CompanyType getByType(CompanySizeType companySizeType) {
        return COMPANY_SIZE_TYPE_MAP.get(companySizeType);
    }
}
