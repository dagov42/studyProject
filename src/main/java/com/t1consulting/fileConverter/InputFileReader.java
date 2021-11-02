package com.t1consulting.fileConverter;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.t1consulting.salaryCounter.entities.Company;
import com.t1consulting.salaryCounter.entities.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

public class InputFileReader {
    final Logger log = Logger.getLogger("fileReader");

    private final String filename;
    private final List<Employee> employeeList = new ArrayList<>();

    public InputFileReader(String filename) {
        this.filename = filename;
    }

    public List<Employee> getEmployeeList(Company company) throws IOException, CsvException {
        Path myPath = Paths.get(filename);
        CSVParser parser = new CSVParserBuilder().withSeparator('\n').build();
        try (BufferedReader br = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {
            List<String[]> employees = reader.readAll();
            log.info("Employees list: ");
            for (String[] row : employees) {
                log.info(row[0]);
                List<String> items = Arrays.asList(row[0].split("\\s*,\\s*"));
                employeeList.add(parseStringToEmployee(items, company));
            }
            return employeeList;
        }
    }

    private Employee parseStringToEmployee(List<String> row, Company company) {
        Employee employee = new Employee();
        employee.setCompany(company);
        String[] fullName = row.get(0).split(" ");
        employee.setName(fullName[0]);
        employee.setSurname(fullName[1]);
        employee.setEmployeeID(Integer.valueOf(row.get(1)));
        employee.setBirthDate(LocalDate.parse(row.get(2), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        employee.setSex(row.get(3));
        employee.setEmail(row.get(4));
        employee.setPhoneNumber(row.get(5));
        employee.setPosition(row.get(6));
        employee.setSalary(new BigDecimal(row.get(6)));
        company.addDepartment(row.get(7));
        employee.setDepartment(company.getDepartment(row.get(7)));
        company.getDepartment(row.get(7)).addEmployee(employee);
        return employee;
    }
}
