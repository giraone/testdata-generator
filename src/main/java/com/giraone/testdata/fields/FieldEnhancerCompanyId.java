package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class adds a "companyId" string value to the person.
 * The companies are of three types
 * <ul>
 *     <li>small:  90% with employee numbers from 2 to 20/li>
 *     <li>medium:  9% with employee numbers from 20 to 500/li>
 *     <li>large:   1% with employee numbers from 500 to 10000/li>
 * </ul>
 */
public class FieldEnhancerCompanyId implements FieldEnhancer {

    private static final Random RANDOM = new Random();

    private static final HashMap<CompanyType.CompanySizeType, List<Company>> COMPANY_CACHE = new HashMap<>();

    private static final int totalNumberOfCompanies = 10000;

    static {

        COMPANY_CACHE.put(CompanyType.CompanySizeType.small, new ArrayList<>());
        COMPANY_CACHE.put(CompanyType.CompanySizeType.medium, new ArrayList<>());
        COMPANY_CACHE.put(CompanyType.CompanySizeType.large, new ArrayList<>());
        for (int i = 0; i < totalNumberOfCompanies; i++) {
            final Company company = generateCompany();
            COMPANY_CACHE.get(company.getCompanySizeType()).add(company);
        }
    }

    public void addFields(GeneratorConfiguration configuration, String field, Person person) {

        final String value = randomCompanyId();
        person.setAdditionalField("companyId", value);
    }

    private Company randomCompany() {

        final int percent = RANDOM.nextInt(100);

        final CompanyType.CompanySizeType companySizeType;
        if (percent > 9) {
            companySizeType = CompanyType.CompanySizeType.small;
        } else if (percent > 0) {
            companySizeType = CompanyType.CompanySizeType.medium;
        } else {
            companySizeType = CompanyType.CompanySizeType.large;
        }
        List<Company> companies = COMPANY_CACHE.get(companySizeType);
        return companies.get(RANDOM.nextInt(companies.size()));
    }

    private String randomCompanyId() {

        return randomCompany().getId();
    }

    //------------------------------------------------------------------------------------------------------------------

    private static Company generateCompany() {

        final CompanyType.CompanySizeType companySizeType;
        final int percent = RANDOM.nextInt(100);
        if (percent > 9) {
            companySizeType = CompanyType.CompanySizeType.small;
        } else if (percent > 0) {
            companySizeType = CompanyType.CompanySizeType.medium;
        } else {
            companySizeType = CompanyType.CompanySizeType.large;
        }

        CompanyType companyType = CompanyType.getByType(companySizeType);
        final int numberOfEmployees = companyType.getMinimalNumberOfEmployees() + RANDOM.nextInt(
                companyType.getMinimalNumberOfEmployees() + companyType.getMaximalNumberOfEmployees());
        final int companyIndex = COMPANY_CACHE.get(companySizeType).size();
        String companyId = String.format("%s-%08d-%08d",
                companySizeType.name().substring(0, 1), companyIndex, numberOfEmployees);
        return new Company(companyId, numberOfEmployees, companySizeType);
    }
}