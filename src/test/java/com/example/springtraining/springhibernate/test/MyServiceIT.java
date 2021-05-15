package com.example.springtraining.springhibernate.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MyServiceIT {

    @Autowired
    private MyRepository repository;

    @Autowired
    private MyService service;

    @Test
    public void shouldAddNewEntity() {
        //given
        MyEntity myEntity = new MyEntity("name1", "value1");

        //when
        service.add(myEntity);

        //then
        Optional<MyEntity> byName = repository.getByName("name1");
        assertThat(byName.isPresent()).isTrue();
        assertThat(byName.get().getValue()).isEqualToIgnoringCase("value1");
    }

    @Test
    public void shouldNotAddEntityForAlreadyTakenName() {
        //given
        MyEntity myEntit2 = new MyEntity("name2", "value2");
        MyEntity myEntit3 = new MyEntity("name2", "value3");

        //then
        assertThrows(RuntimeException.class, () -> {
            service.add(myEntit2);
            service.add(myEntit3);
        });
    }

}
