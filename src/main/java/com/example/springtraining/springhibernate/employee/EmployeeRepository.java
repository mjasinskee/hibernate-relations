package com.example.springtraining.springhibernate.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findEmployeeByName(String name);
    Optional<Employee> findFirstByDepartment(Department department);
    long countByDepartment(Department department);


    long countEmployeeByDepartment(Department department);
    long countEmployeeByDepartmentAndSalaryLessThan(Department department, BigDecimal salary);

    @Query("select e from Employee e where e.department = :department")
    Optional<Employee> searchByDepartment(@Param("department") Department department);
}
