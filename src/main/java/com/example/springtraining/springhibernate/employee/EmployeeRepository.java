package com.example.springtraining.springhibernate.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeByName(String name);
    Optional<Employee> findFirstByDepartment(Department department);
    long countByDepartment(Department department);


    long countEmployeeByDepartment(Department department);
    long countByDepartmentAndSalaryIsLessThan(Department department, BigDecimal salary);
    Set<Employee> findAllByDepartmentAndSalaryIsLessThan(Department department, BigDecimal salary);

    @Query("select avg(e.salary) from Employee e where e.position = :position")
    double calculateAverageSalaryOnPosition(@Param("position") String position);

    @Query("select e from Employee e where e.department = :department")
    Optional<Employee> searchByDepartment(@Param("department") Department department);
}
