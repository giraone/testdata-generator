package com.giraone.testdata.fields.company;

import java.util.List;

public class CompanyLevelSpecification {

    private String valuePattern;
    private List<CompanySizeDistribution> sizeDistribution;

    public CompanyLevelSpecification() {
    }

    public CompanyLevelSpecification(String valuePattern, List<CompanySizeDistribution> sizeDistribution) {
        this.valuePattern = valuePattern;
        this.sizeDistribution = sizeDistribution;
    }

    public String getValuePattern() {
        return valuePattern;
    }

    public void setValuePattern(String valuePattern) {
        this.valuePattern = valuePattern;
    }

    public List<CompanySizeDistribution> getSizeDistribution() {
        return sizeDistribution;
    }

    public void setSizeDistribution(List<CompanySizeDistribution> sizeDistribution) {
        this.sizeDistribution = sizeDistribution;
    }

    @Override
    public String toString() {
        return "CompanyLevelSpecification{" +
            "valuePattern='" + valuePattern + '\'' +
            ", sizeDistribution=" + sizeDistribution +
            '}';
    }
}
