package com.example.springtraining.springhibernate.example;

class SampleEntry {

    private String name;
    private String value;

    public SampleEntry(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SampleEntry{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
