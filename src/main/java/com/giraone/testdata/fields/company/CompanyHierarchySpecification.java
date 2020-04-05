package com.giraone.testdata.fields.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompanyHierarchySpecification {

    public static final CompanyHierarchySpecification DEFAULT = new CompanyHierarchySpecification();

    static
    {
        CompanyLevelSpecification companyLevelSpecification = new CompanyLevelSpecification();
        DEFAULT.setLevelSpecifications(Collections.singletonList(companyLevelSpecification));

        CompanySizeDistribution companySizeDistributionSmall = new CompanySizeDistribution(
            "small", 0.8f, 1, 99
        );
        CompanySizeDistribution companySizeDistributionLarge = new CompanySizeDistribution(
            "large", 0.2f, 100, 1000
        );
        List<CompanySizeDistribution> sizeDistributions = new ArrayList<>();
        sizeDistributions.add(companySizeDistributionSmall);
        sizeDistributions.add(companySizeDistributionLarge);
        companyLevelSpecification.setValuePattern("%s-%08d");
        companyLevelSpecification.setSizeDistribution(sizeDistributions);
    }

    private String delimiter = "/";
    private List<CompanyLevelSpecification> levelSpecifications;

    public CompanyHierarchySpecification() {
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @SuppressWarnings("unused")
    public CompanyHierarchySpecification(List<CompanyLevelSpecification> levelSpecifications) {
        this.levelSpecifications = levelSpecifications;
    }

    public List<CompanyLevelSpecification> getLevelSpecifications() {
        return levelSpecifications;
    }

    public void setLevelSpecifications(List<CompanyLevelSpecification> levelSpecifications) {
        this.levelSpecifications = levelSpecifications;
    }

    @Override
    public String toString() {
        return "CompanyHierarchySpecification{" +
            "delimiter='" + delimiter + '\'' +
            ", levelSpecifications=" + levelSpecifications +
            '}';
    }
}
