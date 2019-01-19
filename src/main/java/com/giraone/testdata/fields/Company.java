package com.giraone.testdata.fields;

class Company {

    private String id;
    private int numberOfEmployees;
    private CompanyType.CompanySizeType companySizeType;

    public Company(String id, int numberOfEmployees, CompanyType.CompanySizeType companySizeType) {
        this.id = id;
        this.numberOfEmployees = numberOfEmployees;
        this.companySizeType = companySizeType;
    }

    public String getId() {
        return id;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public CompanyType.CompanySizeType getCompanySizeType() {
        return companySizeType;
    }
}
