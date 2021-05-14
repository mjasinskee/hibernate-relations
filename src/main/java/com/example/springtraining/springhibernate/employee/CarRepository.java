package com.example.springtraining.springhibernate.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query("select c from Car c where c.yearOfProduction = (select min (cc.yearOfProduction) from Car cc )")
    Optional<Car> findOldest();

    @Query("select avg (c.yearOfProduction) from Car c")
    Double findAverageAge();
}
