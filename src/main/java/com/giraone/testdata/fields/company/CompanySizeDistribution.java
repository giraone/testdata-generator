package com.giraone.testdata.fields.company;

public class CompanySizeDistribution {

    private String name;
    private float proportion;
    private int minimalNumber;
    private int maximalNumber;

    public CompanySizeDistribution() {
    }

    public CompanySizeDistribution(String name, float proportion, int minimalNumber, int maximalNumber) {
        this.name = name;
        this.proportion = proportion;
        this.minimalNumber = minimalNumber;
        this.maximalNumber = maximalNumber;
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

    public int getMinimalNumber() {
        return minimalNumber;
    }

    public void setMinimalNumber(int minimalNumber) {
        this.minimalNumber = minimalNumber;
    }

    public int getMaximalNumber() {
        return maximalNumber;
    }

    public void setMaximalNumber(int maximalNumber) {
        this.maximalNumber = maximalNumber;
    }

    @Override
    public String toString() {
        return "CompanySizeDistribution{" +
            "name='" + name + '\'' +
            ", proportion=" + proportion +
            ", minimalNumber=" + minimalNumber +
            ", maximalNumber=" + maximalNumber +
            '}';
    }
}
