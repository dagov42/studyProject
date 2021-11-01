package com.t1consulting.salaryCounter.entities;

import java.util.*;
import java.util.logging.Logger;

public class Company {
    private String companyName;
    private Set<Department> departments;
    private List<Employee> employees;
    private List<String[]> possibleToTransferEmployees = new ArrayList<>();
    final Logger log = Logger.getLogger("Company");

    public Company(String companyName) {
        this.companyName = companyName;
        this.departments = new HashSet<>();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean addDepartment(String departmentName) {
        for (Department department : departments) {
            if (!department.getName().equals(departmentName)) {
                new Department(departmentName);
                return true;
            }
        }
        return false;
    }

    public Department getDepartment(String name) {
        for (Department department : departments) {
            if (name.equals(department.getName())) {
                return department;
            }
        }
        return null;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void addEmployees(List<Employee> employees, Company company) {
        for (Employee employee : employees) {
            employee.setCompany(company);
        }
    }

    public List<Employee> getAllEmployees() {
        employees = new ArrayList<>();
        for (Department department : departments) {
            employees.addAll(department.getEmployees());
        }
        return employees;
    }

    public List<String[]> getPossibleToTransferEmployees() {
        List<String[]> result = new ArrayList<>(); // output format for OpenCSV
        List<Map<Employee, Employee>> fromTo = new ArrayList<>();
        List<Department> departmentList = departments.stream().toList();
        for (int i = 0; i < departmentList.size(); i++) {
            for (int j = i + 1; j < departmentList.size(); j++) {
                fromTo.add(findEmployeesToTransferInTwoDept(departmentList.get(i), departmentList.get(j)));
            }
        }
        for (Map<Employee, Employee> employeeEmployeeMap : fromTo) {
            for (Map.Entry<Employee, Employee> entry : employeeEmployeeMap.entrySet()) {
                String[] line = {entry.getKey().getSurname(), entry.getKey().getName()," change with ", entry.getValue().getSurname(), entry.getValue().getName()};
                result.add(line);
            }
        }
        return result;
    }

    private Map<Employee, Employee> findEmployeesToTransferInTwoDept(Department first, Department second) {
        Map<Employee, Employee> transferEmployees = new HashMap<>();
        if (first.getEmployees().isEmpty() || second.getEmployees().isEmpty()) {
            log.warning("One of Department is empty");
        } else {
            // add logic to find pair of Employee
        }

        return transferEmployees;
    }
}
