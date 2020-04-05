package com.giraone.testdata.fields.company;

public class CompanySizeDistribution {

    private String name;
    private float proportion;
    private int minimalNumberOfEmployees;
    private int maximalNumberOfEmployees;
    private int minimalNumberOfSubCompanies;
    private int maximalNumberOfSubCompanies;

    public CompanySizeDistribution() {
    }

    public CompanySizeDistribution(String name, float proportion) {
        this.name = name;
        this.proportion = proportion;
        this.minimalNumberOfEmployees = -1;
        this.maximalNumberOfEmployees = -1;
    }

    public CompanySizeDistribution(String name, float proportion, int minimalNumberOfEmployees, int maximalNumberOfEmployees) {
        this.name = name;
        this.proportion = proportion;
        this.minimalNumberOfEmployees = minimalNumberOfEmployees;
        this.maximalNumberOfEmployees = maximalNumberOfEmployees;
    }

    public CompanySizeDistribution(String name, float proportion, int minimalNumberOfEmployees, int maximalNumberOfEmployees, int minimalNumberOfSubCompanies, int maximalNumberOfSubCompanies) {
        this.name = name;
        this.proportion = proportion;
        this.minimalNumberOfEmployees = minimalNumberOfEmployees;
        this.maximalNumberOfEmployees = maximalNumberOfEmployees;
        this.minimalNumberOfSubCompanies = minimalNumberOfSubCompanies;
        this.maximalNumberOfSubCompanies = maximalNumberOfSubCompanies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }

    public int getMinimalNumberOfEmployees() {
        return minimalNumberOfEmployees;
    }

    public void setMinimalNumberOfEmployees(int minimalNumberOfEmployees) {
        this.minimalNumberOfEmployees = minimalNumberOfEmployees;
    }

    public int getMaximalNumberOfEmployees() {
        return maximalNumberOfEmployees;
    }

    public void setMaximalNumberOfEmployees(int maximalNumberOfEmployees) {
        this.maximalNumberOfEmployees = maximalNumberOfEmployees;
    }

    public int getMinimalNumberOfSubCompanies() {
        return minimalNumberOfSubCompanies;
    }

    public void setMinimalNumberOfSubCompanies(int minimalNumberOfSubCompanies) {
        this.minimalNumberOfSubCompanies = minimalNumberOfSubCompanies;
    }

    public int getMaximalNumberOfSubCompanies() {
        return maximalNumberOfSubCompanies;
    }

    public void setMaximalNumberOfSubCompanies(int maximalNumberOfSubCompanies) {
        this.maximalNumberOfSubCompanies = maximalNumberOfSubCompanies;
    }


}
