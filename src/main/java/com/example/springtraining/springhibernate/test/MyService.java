package com.example.springtraining.springhibernate.test;

import java.util.Optional;

class MyService {

    private final MyRepository repository;

    MyService(MyRepository repository) {
        this.repository = repository;
    }

    void add(MyEntity entity) {
        Optional<MyEntity> byName = repository.getByName(entity.getName());
        if (byName.isPresent()) {
            throw new RuntimeException("name aleardy taken");
        }
        repository.add(entity);
    }

}
