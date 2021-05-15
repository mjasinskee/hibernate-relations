package com.example.springtraining.springhibernate.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ExampleController {

    private final SampleRepository repository;

    ExampleController(SampleRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    SampleEntry hello() {
        return repository.get("name").get();
    }
}
