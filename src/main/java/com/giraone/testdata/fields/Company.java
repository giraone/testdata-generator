package com.giraone.testdata.fields;

class Company {

    private String id;
    private CompanyType.CompanySizeType companySizeType;

    public Company(String id, CompanyType.CompanySizeType companySizeType) {
        this.id = id;
        this.companySizeType = companySizeType;
    }

    public String getId() {
        return id;
    }

    public CompanyType.CompanySizeType getCompanySizeType() {
        return companySizeType;
    }
}
