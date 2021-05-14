package com.example.springtraining.springhibernate.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void should() {
        //given
        carRepository.save(new Car("Toyota", 3, "red", 1990));
        carRepository.save(new Car("Toyota", 3, "red", 1991));
        carRepository.save(new Car("Toyota", 3, "red", 1992));
        carRepository.save(new Car("Toyota", 3, "red", 1993));

        //when
        Optional<Car> oldest = carRepository.findOldest();

        //then
        System.out.println(oldest.get());

        Double averageAge = carRepository.findAverageAge();
        System.out.println(averageAge);
    }

}
