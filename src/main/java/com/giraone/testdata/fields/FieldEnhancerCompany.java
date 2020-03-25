package com.giraone.testdata.fields;

import com.giraone.testdata.Person;
import com.giraone.testdata.generator.GeneratorConfiguration;

import java.util.*;

/**
 * This class adds a "companyId" string value to the person.
 * The companies are of three types
 * <ul>
 * <li>small:  90% with employee numbers from 2 to 20/li>
 * <li>medium:  9% with employee numbers from 20 to 500/li>
 * <li>large:   1% with employee numbers from 500 to 10000/li>
 * </ul>
 */
@SuppressWarnings("unused")
public class FieldEnhancerCompany implements FieldEnhancer {

    private static final Random RANDOM = new Random();

    private static final Map<CompanyType.CompanySizeType, List<Company>> COMPANY_CACHE = new HashMap<>();

    private static final int totalNumberOfCompanies = 10000;

    static {

        COMPANY_CACHE.put(CompanyType.CompanySizeType.small, new ArrayList<>());
        COMPANY_CACHE.put(CompanyType.CompanySizeType.medium, new ArrayList<>());
        COMPANY_CACHE.put(CompanyType.CompanySizeType.large, new ArrayList<>());
        for (int i = 0; i < totalNumberOfCompanies; i++) {
            final Company company = generateCompany();
            COMPANY_CACHE.get(company.getCompanySizeType()).add(company);
        }
        System.out.println("Companies with size s:" + COMPANY_CACHE.get(CompanyType.CompanySizeType.small).size());
        System.out.println("Companies with size m:" + COMPANY_CACHE.get(CompanyType.CompanySizeType.medium).size());
        System.out.println("Companies with size l:" + COMPANY_CACHE.get(CompanyType.CompanySizeType.large).size());
    }

    public void addFields(GeneratorConfiguration configuration, Person person, String field) {

        final Company company = randomCompany();
        final String companyId = company.getId();
        setAdditionalField(configuration, person, FieldConstants.companyId, companyId);

        if (configuration.additionalFields.containsKey(FieldConstants.personnelNumber)) {
            final String personnelNumber = company.getNextPersonnelNumber();
            setAdditionalField(configuration, person, FieldConstants.personnelNumber, personnelNumber);
        }
    }

    private Company randomCompany() {

        final CompanyType.CompanySizeType companySizeType;
        final int i = RANDOM.nextInt(3);
        if (i == 0) {
            companySizeType = CompanyType.CompanySizeType.small;
        } else if (i == 1) {
            companySizeType = CompanyType.CompanySizeType.medium;
        } else {
            companySizeType = CompanyType.CompanySizeType.large;
        }
        final List<Company> companies = COMPANY_CACHE.get(companySizeType);
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

        /*
        CompanyType companyType = CompanyType.getByType(companySizeType);
        final int numberOfEmployees = companyType.getMinimalNumberOfEmployees() + RANDOM.nextInt(
                companyType.getMinimalNumberOfEmployees() + companyType.getMaximalNumberOfEmployees());
        */
        final int companyIndex = COMPANY_CACHE.get(companySizeType).size();
        final String companyId = String.format("%s-%08d",
                companySizeType.name().substring(0, 1), companyIndex);
        return new Company(companyId, companySizeType);
    }
}