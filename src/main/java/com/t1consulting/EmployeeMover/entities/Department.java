package com.t1consulting.EmployeeMover.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Department {

    private String name;
    private List<Employee> employees;
    final Logger log = Logger.getLogger("Department");

    public Department(String name) {
        this.name = name;
        this.employees = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public BigDecimal getTotalDepartmentSalary() {
        return employees.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getAverageSalary() {
        BigDecimal averageDepartmentSalary = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
        for (Employee employee : employees) {
            averageDepartmentSalary = averageDepartmentSalary.add(employee.getSalary());
        }
        return averageDepartmentSalary.divide(new BigDecimal(employees.size()), 2, RoundingMode.HALF_UP);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

}
