package com.example.springtraining.springhibernate.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SampleServiceIT {

    @Autowired
    private SampleService service;

    @Autowired
    private SampleRepository repository;

    @Test
    public void shouldAddEntry() {
        //given
        SampleEntry entry = new SampleEntry("name", "value");

        //when
        service.add(entry);

        //then
        Optional<SampleEntry> sampleEntry = repository.get("name");
        assertThat(sampleEntry.isPresent()).isTrue();
        assertThat(sampleEntry.get().getValue()).isEqualTo("value");
    }


    @Test
    public void shouldNotAddEntryForAlreadyTakenName() {
        //given
        SampleEntry entry1 = new SampleEntry("name1", "value1");
        SampleEntry entry2 = new SampleEntry("name1", "value2");

        //then
        assertThrows(RuntimeException.class, () -> {
            service.add(entry1);
            service.add(entry2);
        });
    }
}
