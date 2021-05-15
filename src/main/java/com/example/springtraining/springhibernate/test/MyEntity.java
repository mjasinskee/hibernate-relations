package com.example.springtraining.springhibernate.test;

class MyEntity {

    private String name;
    private String value;

    public MyEntity(String name, String value) {
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
        return "MyEntity{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
