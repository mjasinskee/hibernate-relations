package com.example.springtraining.springhibernate.employee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootTest
class EmployeeRepositoryIT {

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void should() {
        repository.save(new Employee("name1", Department.IT, "position 1", BigDecimal.valueOf(11000)));
        repository.save(new Employee("name2", Department.IT, "position 1", BigDecimal.valueOf(12000)));
        repository.save(new Employee("name3", Department.IT, "position 1", BigDecimal.valueOf(13000)));
        repository.save(new Employee("name4", Department.IT, "position 2", BigDecimal.valueOf(14000)));
        repository.save(new Employee("name4", Department.HR, "position 2", BigDecimal.valueOf(15000)));
//        List<Employee> all = repository.findAll();
//        all.stream().forEach(employee -> System.out.println(employee));
//        Optional<Employee> name1 = repository.findEmployeeByName("name1");
//        System.out.println("employee: " + name1.isPresent());
//        System.out.println("employee: " + name1.get());
//        Optional<Employee> firstByDepartment = repository.findFirstByDepartment(Department.IT);
//        System.out.println("result");
//        System.out.println(firstByDepartment.get());
        System.out.println("count: " + repository.countByDepartment(Department.IT));
        System.out.println("count: " + repository.countByDepartmentAndSalaryIsLessThan(Department.IT, BigDecimal.valueOf(14000)));
        System.out.println("list: " + repository.findAllByDepartmentAndSalaryIsLessThan(Department.IT, BigDecimal.valueOf(14000)));
        System.out.println("result search: " + repository.searchByDepartment(Department.HR));
        System.out.println("avg salary: " + repository.calculateAverageSalaryOnPosition("position 2"));
    }

    @Test
    public void shouldMatchNumber() {
        //given
        Pattern pattern = Pattern.compile("\\+(\\d{2})(-\\d{3}){3}");

        //when
        Matcher matcher = pattern.matcher("+48-123-123-123");

        //then
        System.out.println(matcher.matches());
    }
}
