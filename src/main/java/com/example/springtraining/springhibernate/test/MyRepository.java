package com.example.springtraining.springhibernate.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class MyRepository {

    private final List<MyEntity> entities = new ArrayList<>();

    void add(MyEntity myEntity) {
        entities.add(myEntity);
    }

    Optional<MyEntity> getByName(String name) {
        return entities.stream().filter(myEntity -> myEntity.getName().equals(name)).findFirst();
    }
}
