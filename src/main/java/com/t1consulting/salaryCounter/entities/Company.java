package com.t1consulting.salaryCounter.entities;

import java.math.BigDecimal;
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

    public void addDepartment(String departmentName) {
        if (this.getDepartment(departmentName) == null) {
            log.info("Department " + departmentName + " added");
            departments.add(new Department(departmentName));
        }
    }

    public Department getDepartment(String name) {
        for (Department department : departments) {
            if (name.equals(department.getName())) {
                return department;
            }
        }
        log.info("Department " + name + " doesn't exist");
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
                String[] line = {entry.getKey().getName() + " " + entry.getKey().getSurname()
                        + " from " + entry.getKey().getDepartment().getName() + " change with "
                        + entry.getValue().getName() + " " + entry.getValue().getSurname()
                        + " from " + entry.getValue().getDepartment().getName()};
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
            BigDecimal firstDeptAVGSalary = first.getAverageSalary();
            BigDecimal secondDeptAVGSalary = second.getAverageSalary();
            for (int i = 0; i < first.getEmployees().size(); i++) {
                for (int j = i + 1; j < second.getEmployees().size(); j++) {
                    Employee firstDeptEmp = first.getEmployees().get(i);
                    Employee secondDeptEmp = second.getEmployees().get(j);
                    first.addEmployee(secondDeptEmp);
                    second.addEmployee(firstDeptEmp);
                    first.removeEmployee(firstDeptEmp);
                    second.removeEmployee(secondDeptEmp);
                    if (first.getAverageSalary().compareTo(firstDeptAVGSalary) > 0 && second.getAverageSalary().compareTo(secondDeptAVGSalary) > 0) {
                        transferEmployees.put(firstDeptEmp, secondDeptEmp);
                    }
                    first.addEmployee(firstDeptEmp);
                    second.addEmployee(secondDeptEmp);
                    first.removeEmployee(secondDeptEmp);
                    second.removeEmployee(firstDeptEmp);
                }
            }
        }

        return transferEmployees;
    }
}
