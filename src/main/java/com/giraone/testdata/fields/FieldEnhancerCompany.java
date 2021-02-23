package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.fields.company.Company;
import com.giraone.testdata.fields.company.CompanyHierarchySpecification;
import com.giraone.testdata.fields.company.CompanyLevelSpecification;
import com.giraone.testdata.fields.company.CompanySizeDistribution;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.util.List;

/**
 * This class adds a "company.id" string value to the person.
 * The companyId is a hierarchical key, e.g. "Alphabet/Google/Sales/US
 */
@SuppressWarnings("unused")
public class FieldEnhancerCompany implements FieldEnhancer {

    private CompanyHierarchySpecification companyHierarchySpecification;
    private int totalNumberOfCompanies;
    private Company[] companyCache;

    public FieldEnhancerCompany() {
    }

    public FieldEnhancerCompany(CompanyHierarchySpecification companyHierarchySpecification, int totalNumberOfPersons) {
        init(companyHierarchySpecification, totalNumberOfPersons);
    }

    public void init(CompanyHierarchySpecification companyHierarchySpecification, int totalNumberOfPersons) {
        this.companyHierarchySpecification = companyHierarchySpecification;
        this.totalNumberOfCompanies = calculateTotalNumberOfCompanies(totalNumberOfPersons, companyHierarchySpecification.getLevelSpecifications().get(0));
        System.err.println("totalNumberOfPersons = " + totalNumberOfPersons);
        System.err.println("totalNumberOfCompanies = " + totalNumberOfCompanies);
        this.companyCache = new Company[totalNumberOfCompanies];
    }

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        final Company company = randomCompany();
        if (configuration.containsAdditionalField(FieldConstants.companyId)) {
            setAdditionalField(configuration, person, FieldConstants.companyId, company.getKey());
        }
        if (configuration.containsAdditionalField(FieldConstants.companyIndex)) {
            setAdditionalField(configuration, person, FieldConstants.companyIndex, Long.toString(company.getIndex()));
        }
        if (configuration.containsAdditionalField(FieldConstants.companyNumberOfEmployees)) {
            setAdditionalField(configuration, person, FieldConstants.companyNumberOfEmployees, Long.toString(company.getTotalNumberOfEmployees()));
        }
        if (configuration.containsAdditionalField(FieldConstants.personnelIndex)) {
            setAdditionalField(configuration, person, FieldConstants.personnelIndex, Integer.toString(company.getNextPersonnelIndex()));
        }
    }

    Company randomCompany() {

        final int companyIndex = RANDOM.nextInt(this.totalNumberOfCompanies);

        Company company = this.companyCache[companyIndex];
        if (company != null) {
            return company;
        }

        int levelIndex = 0;
        CompanyLevelSpecification levelSpecification0 = companyHierarchySpecification.getLevelSpecifications().get(levelIndex);
        final int distributionIndex = getDistribution(levelSpecification0.getSizeDistribution(), RANDOM.nextFloat());
        final CompanySizeDistribution distribution = levelSpecification0.getSizeDistribution().get(distributionIndex);
        final int totalNumberOfEmployees = distribution.getMinimalNumberOfEmployees() + RANDOM.nextInt(distribution.getMaximalNumberOfEmployees() - distribution.getMinimalNumberOfEmployees());
        String format = levelSpecification0.getValuePattern();
        final String companyKey;
        if (format.contains("%s")) {
            companyKey = String.format(format, distribution.getName(), companyIndex);
        } else {
            companyKey = String.format(format, companyIndex);
        }
        final int totalNumberOfSubCompanies = calculateTotalNumberOfSubCompanies(totalNumberOfEmployees, levelSpecification0);
        company = new Company(levelIndex, companyIndex, companyKey, totalNumberOfEmployees, totalNumberOfSubCompanies);
        // System.err.println(company);
        this.companyCache[companyIndex] = company;

        if (totalNumberOfSubCompanies > 0) {
            final int numberOfLevels = companyHierarchySpecification.getLevelSpecifications().size();
            for (levelIndex = 1; levelIndex < numberOfLevels; levelIndex++) {
                CompanyLevelSpecification levelSpecification = companyHierarchySpecification.getLevelSpecifications().get(levelIndex);
                company = randomCompany(company, companyHierarchySpecification.getDelimiter(), levelSpecification);
                // System.err.println("    ".substring(0, levelIndex) + company);
            }
        }

        return company;
    }

    public String formatPersonnelNumber(long index) {
        return String.format("%08d", index);
    }

    //------------------------------------------------------------------------------------------------------------------

    private Company randomCompany(Company parentCompany, String delimiter, CompanyLevelSpecification levelSpecification) {

        final int companyIndex = RANDOM.nextInt(parentCompany.getSubCompanies().length);
        Company subCompany = parentCompany.getSubCompanies()[companyIndex];
        if (subCompany != null) {
            return subCompany;
        }
        final int distributionIndex = getDistribution(levelSpecification.getSizeDistribution(), RANDOM.nextFloat());
        final CompanySizeDistribution distribution = levelSpecification.getSizeDistribution().get(distributionIndex);
        int max = 1 + (int) Math.round(((parentCompany.getTotalNumberOfEmployees() - 1) * distribution.getProportion()) - 0.5);
        int min = 1;
        final int totalNumberOfEmployees = min + RANDOM.nextInt(1 + max - min);
        final String companyKey = parentCompany.getKey() + delimiter + String.format(levelSpecification.getValuePattern(), distribution.getName(), companyIndex);
        final int totalNumberOfSubCompanies = calculateTotalNumberOfSubCompanies(totalNumberOfEmployees, levelSpecification);
        subCompany = new Company(parentCompany.getLevel() + 1, companyIndex, companyKey, totalNumberOfEmployees, totalNumberOfSubCompanies);
        parentCompany.addSubCompany(companyIndex, subCompany);
        return subCompany;
    }

    private int calculateTotalNumberOfCompanies(int totalNumberOfPersons, CompanyLevelSpecification levelSpecification0) {

        float averageCompanySize = 0.0f;
        for (int distributionIndex = 0; distributionIndex < levelSpecification0.getSizeDistribution().size(); distributionIndex++) {
            CompanySizeDistribution distribution = levelSpecification0.getSizeDistribution().get(distributionIndex);
            int average = distribution.getMinimalNumberOfEmployees() + (distribution.getMaximalNumberOfEmployees() - distribution.getMinimalNumberOfEmployees()) / 2;
            averageCompanySize += average * distribution.getProportion();
            //System.err.println(average + " " + distribution.getProportion() + " " + averageCompanySize);
        }
        System.err.println("Average company size = " + averageCompanySize);
        if (averageCompanySize <= 0.5f) {
            throw new IllegalArgumentException("CompanyLevelSpecification is incorrect and lead to companies with 0 employees!");
        }
        return Math.max(1, Math.round(totalNumberOfPersons / averageCompanySize - 0.5f));
    }

    private int calculateTotalNumberOfSubCompanies(int totalNumberOfPersons, CompanyLevelSpecification levelSpecification) {

        int ret = 0;
        for (int distributionIndex = 0; distributionIndex < levelSpecification.getSizeDistribution().size(); distributionIndex++) {
            CompanySizeDistribution distribution = levelSpecification.getSizeDistribution().get(distributionIndex);
            ret += distribution.getMinimalNumberOfSubCompanies() - 1 + RANDOM.nextInt(1 + distribution.getMaximalNumberOfSubCompanies() - distribution.getMinimalNumberOfSubCompanies());
        }
        return ret;
    }

    private int getDistribution(List<CompanySizeDistribution> distributions, float randomMax) {

        float sum = 0.0f;
        for (int index = 0; index < distributions.size(); index++) {
            CompanySizeDistribution distribution = distributions.get(index);
            sum += distribution.getProportion();
            if (sum > randomMax) {
                return index;
            }
        }
        return distributions.size() - 1;
    }
}