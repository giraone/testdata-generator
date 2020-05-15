package com.giraone.testdata.fields;

import com.giraone.testdata.fields.company.Company;
import com.giraone.testdata.fields.company.CompanyHierarchySpecification;
import com.giraone.testdata.fields.company.CompanyLevelSpecification;
import com.giraone.testdata.fields.company.CompanySizeDistribution;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CompanyHierarchyGenerationTest {

    @Test
    public void test_singleLevel() {

        // arrange
        int numberOfPersons = 10000;

        CompanyHierarchySpecification companyHierarchySpecification = new CompanyHierarchySpecification();
        CompanyLevelSpecification companyLevelSpecification = new CompanyLevelSpecification();
        companyHierarchySpecification.setLevelSpecifications(Collections.singletonList(companyLevelSpecification));

        CompanySizeDistribution companySizeDistributionSmall = new CompanySizeDistribution(
            "s", 0.8f, 1, 99
        );
        CompanySizeDistribution companySizeDistributionLarge = new CompanySizeDistribution(
            "l", 0.2f, 100, 1000
        );
        List<CompanySizeDistribution> sizeDistributions = new ArrayList<>();
        sizeDistributions.add(companySizeDistributionSmall);
        sizeDistributions.add(companySizeDistributionLarge);
        companyLevelSpecification.setValuePattern("%s-%08d");
        companyLevelSpecification.setSizeDistribution(sizeDistributions);

        // act
        FieldEnhancerCompany fieldEnhancerCompany = new FieldEnhancerCompany(companyHierarchySpecification, numberOfPersons);
        Company company = fieldEnhancerCompany.randomCompany();

        // assert
        assertThat(company).isNotNull();
        assertThat(company.getIndex()).isBetween(0, numberOfPersons / 10);
        assertThat(company.getKey()).matches("^(s|l)-[0-9]{8}$");
        assertThat(company.getTotalNumberOfEmployees()).isGreaterThan(0);

        // act/assert loop
        for (int i = 1; i < numberOfPersons; i++) {
            company = fieldEnhancerCompany.randomCompany();
            assertThat(company).isNotNull();
            assertThat(company.getIndex()).isBetween(0, numberOfPersons / 10);
            assertThat(company.getKey()).matches("^(s|l)-[0-9]{8}$");
            assertThat(company.getTotalNumberOfEmployees()).isGreaterThan(0);
        }
    }

    @Test
    public void test_twoLevels() {

        // arrange
        int numberOfPersons = 10000;

        CompanyHierarchySpecification companyHierarchySpecification = new CompanyHierarchySpecification();
        CompanyLevelSpecification companyLevelSpecification0 = new CompanyLevelSpecification();
        CompanyLevelSpecification companyLevelSpecification1 = new CompanyLevelSpecification();
        List<CompanyLevelSpecification> companyLevelSpecificationList = new ArrayList<>();
        companyHierarchySpecification.setLevelSpecifications(companyLevelSpecificationList);
        companyLevelSpecificationList.add(companyLevelSpecification0);
        companyLevelSpecificationList.add(companyLevelSpecification1);
        {
            CompanySizeDistribution companySizeDistributionSmall = new CompanySizeDistribution(
                "s", 0.8f, 1, 99, 0, 2
            );
            CompanySizeDistribution companySizeDistributionLarge = new CompanySizeDistribution(
                "l", 0.2f, 100, 1000, 2, 6
            );
            List<CompanySizeDistribution> sizeDistributions = new ArrayList<>();
            sizeDistributions.add(companySizeDistributionSmall);
            sizeDistributions.add(companySizeDistributionLarge);
            companyLevelSpecification0.setValuePattern("%s-%08d");
            companyLevelSpecification0.setSizeDistribution(sizeDistributions);
        }
        {
            CompanySizeDistribution companySizeDistributionMainDivision = new CompanySizeDistribution(
                "h", 0.5f
            );
            CompanySizeDistribution companySizeDistributionSubDivision = new CompanySizeDistribution(
                "n", 0.5f
            );
            List<CompanySizeDistribution> sizeDistributions = new ArrayList<>();
            sizeDistributions.add(companySizeDistributionMainDivision);
            sizeDistributions.add(companySizeDistributionSubDivision);
            companyLevelSpecification1.setValuePattern("%s-%04d");
            companyLevelSpecification1.setSizeDistribution(sizeDistributions);
        }

        // act
        FieldEnhancerCompany fieldEnhancerCompany = new FieldEnhancerCompany(companyHierarchySpecification, numberOfPersons);
        Company company = fieldEnhancerCompany.randomCompany();

        // assert
        assertThat(company).isNotNull();
        assertThat(company.getIndex()).isBetween(0, numberOfPersons / 10);
        assertThat(company.getKey()).matches("^(s|l)-[0-9]{8}(/(h|n)-[0-9]{4})?$");
        assertThat(company.getTotalNumberOfEmployees()).isGreaterThan(0);

        // act/assert loop
        for (int i = 1; i < numberOfPersons; i++) {
            company = fieldEnhancerCompany.randomCompany();
            assertThat(company).isNotNull();
            assertThat(company.getIndex()).isBetween(0, numberOfPersons / 10);
            assertThat(company.getKey()).matches("^(s|l)-[0-9]{8}(/(h|n)-[0-9]{4})?$");
            assertThat(company.getTotalNumberOfEmployees()).isGreaterThan(0);
        }
    }
}
