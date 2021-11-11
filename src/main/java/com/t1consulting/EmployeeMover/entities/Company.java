package com.t1consulting.EmployeeMover.entities;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

public class Company {
    private String companyName;
    private final Map<String, Department> departments;
    final Logger log = Logger.getLogger("Company");

    public Company(String companyName) {
        this.companyName = companyName;
        this.departments = new HashMap<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Department getOrCreateDepartment(String name) {
        return departments.computeIfAbsent(name, k -> new Department(name));
    }

    public List<Department> getDepartments() {
        return new ArrayList<>(departments.values());
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        for (Department department : departments.values()) {
            employees.addAll(department.getEmployees());
        }
        return employees;
    }

    public List<String> getPossibleToTransferEmployees() {
        List<String> result = new ArrayList<>();
        List<Department> departmentList = getDepartments();
        for (int i = 0; i < departmentList.size(); i++) {
            for (int j = i + 1; j < departmentList.size(); j++) {
                result.addAll(findEmployeesToTransferInTwoDept(departmentList.get(i), departmentList.get(j)));
            }
        }
        return result;
    }

    private List<String> findEmployeesToTransferInTwoDept(Department first, Department second) {
        List<String> result = new ArrayList<>();
        String pattern = "Перевод %s в отдел %s:\n  Средняя ЗП в %s до перевода: %.2f, после: %.2f,\n  Средняя ЗП в %s до перевода: %.2f, после: %.2f;";
        if (first.getEmployees().isEmpty() || second.getEmployees().isEmpty()) {
            log.warning("One of Department is empty");
        } else {
            BigDecimal firstDeptAVGSalary = first.getAverageSalary();
            BigDecimal secondDeptAVGSalary = second.getAverageSalary();
            for (int i = 0; i < first.getEmployees().size(); i++) {
                Employee firstDeptEmp = first.getEmployees().get(i);
                second.addEmployee(firstDeptEmp);
                first.removeEmployee(firstDeptEmp);
                if (first.getAverageSalary().compareTo(firstDeptAVGSalary) > 0 && second.getAverageSalary().compareTo(secondDeptAVGSalary) > 0) {
                    result.add(String.format(pattern, firstDeptEmp.getName(), second.getName(), first.getName(), firstDeptAVGSalary, first.getAverageSalary(),second.getName(), secondDeptAVGSalary, second.getAverageSalary()));
                }
                first.addEmployee(firstDeptEmp);
                second.removeEmployee(firstDeptEmp);
            }
            for (int i = 0; i < second.getEmployees().size(); i++) {
                Employee secondDeptEmp = second.getEmployees().get(i);
                first.addEmployee(secondDeptEmp);
                second.removeEmployee(secondDeptEmp);
                if (first.getAverageSalary().compareTo(firstDeptAVGSalary) > 0 && second.getAverageSalary().compareTo(secondDeptAVGSalary) > 0) {
                    result.add(String.format(pattern, secondDeptEmp.getName(), first.getName(), second.getName(), secondDeptAVGSalary, second.getAverageSalary(),first.getName(), firstDeptAVGSalary, first.getAverageSalary()));
                }
                second.addEmployee(secondDeptEmp);
                first.removeEmployee(secondDeptEmp);
            }
        }
        return result;
    }
}
