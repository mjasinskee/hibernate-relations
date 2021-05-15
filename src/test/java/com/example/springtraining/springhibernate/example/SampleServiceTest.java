package com.example.springtraining.springhibernate.example;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SampleServiceTest {

    SampleRepository repository = new SampleRepository();
    private SampleService service = new SampleService(repository);

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
