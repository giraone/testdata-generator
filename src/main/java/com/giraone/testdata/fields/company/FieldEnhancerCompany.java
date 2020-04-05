package com.giraone.testdata.fields.company;

import com.giraone.testdata.Person;
import com.giraone.testdata.fields.FieldConstants;
import com.giraone.testdata.fields.FieldEnhancer;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.util.*;

/**
 * This class adds a "companyId" string value to the person.
 * The companyId is a hierarchical key, e.g. "Alphabet/Google/Sales/US
 */
@SuppressWarnings("unused")
public class FieldEnhancerCompany implements FieldEnhancer {

    private static final Random RANDOM = new Random();

    private CompanyHierarchySpecification companyHierarchySpecification;
    private int totalNumberOfCompanies;
    private Company[] companyCache;

    public FieldEnhancerCompany(CompanyHierarchySpecification companyHierarchySpecification, int totalNumberOfPersons) {
        this.companyHierarchySpecification = companyHierarchySpecification;
        this.totalNumberOfCompanies = calculateTotalNumberOfCompanies(totalNumberOfPersons);
        System.err.println("totalNumberOfPersons = " + totalNumberOfPersons);
        System.err.println("totalNumberOfCompanies = " + totalNumberOfCompanies);
        this.companyCache = new Company[totalNumberOfCompanies];
    }

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        final Company company = randomCompany();
        setAdditionalField(configuration, person, FieldConstants.companyId, company.getKey());

        if (configuration.additionalFields.containsKey(FieldConstants.personnelNumber)) {
            final String personnelNumber = getNextPersonnelNumber(company.getSize());
            setAdditionalField(configuration, person, FieldConstants.personnelNumber, personnelNumber);
        }
    }

    Company randomCompany() {

        final int companyIndex = RANDOM.nextInt(this.totalNumberOfCompanies);

        Company company = this.companyCache[companyIndex];
        if (company != null) {
            return company;
        }

        String companyKey;
        int companySize = 0;
        StringBuilder s = new StringBuilder();
        final int numberOfLevels = companyHierarchySpecification.getLevelSpecifications().size();
        for (int levelIndex = 0; levelIndex < numberOfLevels; levelIndex++) {

            if (levelIndex > 0) {
                s.append(companyHierarchySpecification.getDelimiter());
            }

            CompanyLevelSpecification levelSpecification = companyHierarchySpecification.getLevelSpecifications().get(levelIndex);
            final float r = RANDOM.nextInt(10000) / 100f;
            final int distributionIndex = getDistribution(levelSpecification.getSizeDistribution(), r);
            final CompanySizeDistribution distribution = levelSpecification.getSizeDistribution().get(distributionIndex);
            companySize += distribution.getMinimalNumber() + RANDOM.nextInt(distribution.getMaximalNumber() - distribution.getMinimalNumber());
            final int indexKey = levelIndex == 0 ? companyIndex : RANDOM.nextInt(companySize);
            s.append(String.format(levelSpecification.getValuePattern(), distribution.getName(), indexKey));
        }

        companyKey = s.toString();
        company = new Company(companyIndex, companyKey, companySize);
        this.companyCache[companyIndex] = company;
        return company;
    }

    public String getNextPersonnelNumber(int size) {
        return String.format("%08d", size);
    }

    //------------------------------------------------------------------------------------------------------------------

    private int calculateTotalNumberOfCompanies(int totalNumberOfPersons) {

        float ret = 0.0f;
        final int numberOfLevels = companyHierarchySpecification.getLevelSpecifications().size();
        for (int levelIndex = 0; levelIndex < numberOfLevels; levelIndex++) {
            CompanyLevelSpecification levelSpecification = companyHierarchySpecification.getLevelSpecifications().get(levelIndex);
            for (int distributionIndex = 0; distributionIndex < levelSpecification.getSizeDistribution().size(); distributionIndex++) {
                CompanySizeDistribution distribution = levelSpecification.getSizeDistribution().get(distributionIndex);
                int average = distribution.getMinimalNumber() + 2 * (distribution.getMaximalNumber() - distribution.getMinimalNumber());
                ret += average * distribution.getProportion();
            }
        }
        return Math.round(ret - 0.5f);
    }

    private int getDistribution(List<CompanySizeDistribution> distributions, float randomMax) {

        float sum = 0.0f;
        for (int index = 0; index < distributions.size(); index++) {
            CompanySizeDistribution distribution = distributions.get(index);
            sum += distribution.getProportion();
            if (sum < randomMax) {
                return index;
            }
        }
        return distributions.size() - 1;
    }
}