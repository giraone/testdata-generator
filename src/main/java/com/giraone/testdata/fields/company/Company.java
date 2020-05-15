package com.giraone.testdata.fields.company;

public class Company {

    private int level;
    private int index;
    private String key;
    private int totalNumberOfEmployees;
    private int personnelIndex;
    private Company[] subCompanies;

    public Company() {
    }

    public Company(int level, int index, String key, int totalNumberOfEmployees, int totalNumberOfSubCompanies) {
        this.level = level;
        this.index = index;
        this.key = key;
        this.totalNumberOfEmployees = totalNumberOfEmployees;
        this.personnelIndex = 0;
        if (totalNumberOfSubCompanies > 0) {
            subCompanies = new Company[totalNumberOfSubCompanies];
        } else {
            subCompanies = new Company[0];
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTotalNumberOfEmployees() {
        return totalNumberOfEmployees;
    }

    public void setTotalNumberOfEmployees(int totalNumberOfEmployees) {
        this.totalNumberOfEmployees = totalNumberOfEmployees;
    }

    public void addSubCompany(int i, Company company)
    {
        if (subCompanies[i] != null) {
            throw new IllegalStateException("Index " + i + " already set with " + subCompanies[i] + "! cannot add " + company);
        }
       subCompanies[i] = company;
    }

    public Company[] getSubCompanies() {
        return subCompanies;
    }
    public void setSubCompanies(Company[] subCompanies) {
        this.subCompanies = subCompanies;
    }

    public int getNextPersonnelIndex() {
        return ++this.personnelIndex;
    }

    @Override
    public String toString() {
        return "Company{" +
            "level=" + level +
            ", index=" + index +
            ", key='" + key + '\'' +
            ", employeeSize=" + totalNumberOfEmployees +
            ", #subCompanies=" + subCompanies.length +
            '}';
    }


}
