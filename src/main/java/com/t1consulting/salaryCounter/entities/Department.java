package com.t1consulting.salaryCounter.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Department {

    private String name;
    private Integer departmentID;
    private List<Employee> employees;
    private BigDecimal averageDepartmentSalary = new BigDecimal(0).setScale(2, RoundingMode.HALF_UP);
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

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
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
        for (Employee employee : employees) {
            averageDepartmentSalary = averageDepartmentSalary.add(employee.getSalary());
        }
        averageDepartmentSalary = averageDepartmentSalary.divide(new BigDecimal(employees.size()), 2, RoundingMode.HALF_UP);
        return averageDepartmentSalary;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

}
