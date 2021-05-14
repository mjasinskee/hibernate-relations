package com.example.springtraining.springhibernate.example;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
class SampleRepository {

    private final Map<String, SampleEntry> entries = new HashMap<>();

    void add(SampleEntry sampleEntry) {
        entries.put(sampleEntry.getName(), sampleEntry);
    }

    Optional<SampleEntry> get(String name) {
        SampleEntry sampleEntry = entries.get(name);
        return Optional.ofNullable(sampleEntry);
    }

}
