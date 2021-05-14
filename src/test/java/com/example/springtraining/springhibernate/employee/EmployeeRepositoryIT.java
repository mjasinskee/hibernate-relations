package com.example.springtraining.springhibernate.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;


@SpringBootTest
class EmployeeRepositoryIT {

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void should() {
        repository.save(new Employee("name1", Department.IT, BigDecimal.valueOf(12000)));
        repository.save(new Employee("name2", Department.IT, BigDecimal.valueOf(13000)));
        repository.save(new Employee("name3", Department.IT, BigDecimal.valueOf(14000)));
        repository.save(new Employee("name4", Department.IT, BigDecimal.valueOf(15000)));
        repository.save(new Employee("name4", Department.HR, BigDecimal.valueOf(16000)));
//        List<Employee> all = repository.findAll();
//        all.stream().forEach(employee -> System.out.println(employee));
//        Optional<Employee> name1 = repository.findEmployeeByName("name1");
//        System.out.println("employee: " + name1.isPresent());
//        System.out.println("employee: " + name1.get());
//        Optional<Employee> firstByDepartment = repository.findFirstByDepartment(Department.IT);
//        System.out.println("result");
//        System.out.println(firstByDepartment.get());
//        System.out.println("count: " + repository.countByDepartment(Department.IT));
//        System.out.println("count: " + repository.countEmployeeByDepartmentAndSalaryLessThan(Department.IT, BigDecimal.valueOf(2000)));
//        System.out.println("result search: " + repository.searchByDepartment(Department.HR));
    }
}
