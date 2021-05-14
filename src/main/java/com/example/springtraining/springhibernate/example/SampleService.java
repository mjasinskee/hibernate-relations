package com.example.springtraining.springhibernate.example;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class SampleService {

    private final SampleRepository repository;

    SampleService(SampleRepository repository) {
        this.repository = repository;
    }

    void add(SampleEntry entry) {
        Optional<SampleEntry> sampleEntry = repository.get(entry.getName());
        if (sampleEntry.isPresent()) {
            throw new RuntimeException("name already taken");
        }
        repository.add(entry);
    }

}
