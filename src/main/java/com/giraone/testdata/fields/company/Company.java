package com.giraone.testdata.fields.company;

public class Company {

    private int index;
    private String key;
    private int size;

    public Company() {
    }

    public Company(int index, String key, int size) {
        this.index = index;
        this.key = key;
        this.size = size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Company{" +
            "index=" + index +
            ", key='" + key + '\'' +
            ", size=" + size +
            '}';
    }
}
