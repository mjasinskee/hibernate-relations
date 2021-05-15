package com.example.springtraining.springhibernate.test;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MyServiceTest {

    private MyRepository repository = new MyRepository();
    private MyService service = new MyService(repository);

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
