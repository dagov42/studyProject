package com.t1consulting.fileConverter;

import com.opencsv.*;
import com.opencsv.exceptions.CsvValidationException;
import com.t1consulting.salaryCounter.entities.Company;
import com.t1consulting.salaryCounter.entities.Employee;
import org.apache.commons.validator.routines.BigDecimalValidator;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

public class InputFileReader {
    final Logger log = Logger.getLogger("fileReader");

    private final String filename;
    private final List<Employee> employeeList = new ArrayList<>();

    public InputFileReader(String filename) {
        this.filename = filename;
    }

    public List<Employee> parseFileToEmployeeListAndAddToCompany(Company company) {
        try {
            CSVParser parser = new CSVParserBuilder().withSeparator('\n').build();
            CSVReader csvReader = new CSVReaderBuilder(new FileReader(filename)).withCSVParser(parser).build();
            String[] nextEmployee;
            while ((nextEmployee = csvReader.readNext()) != null) {
                for (String line : nextEmployee) {
                    log.info(line);
                    List<String> items = Arrays.asList(line.split("\\s*,\\s*"));
                    employeeList.add(parseStringToEmployee(items, company));
                }
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        } catch (CsvValidationException e) {
            log.warning(e.getLocalizedMessage());
            return Collections.emptyList();
        }
        return employeeList;
    }

    private Employee parseStringToEmployee(List<String> row, Company company) {
        Employee employee = new Employee();
        if (row.isEmpty()) {
            log.warning("Нет данных для создания Employee");
        } else if (row.size() < 8) {
            log.warning("Недостаточно данных для создания Employee, создание на основе имеющихся");
            for (String param : row) {
                parseStringToEmployeeAndSet(param, employee, company);
            }
        } else {
            for (String param : row) {
                parseStringToEmployeeAndSet(param, employee, company);
            }
        }
        return employee;
    }

    private void parseStringToEmployeeAndSet(String param, Employee employee, Company company) {
        if (param.equalsIgnoreCase("male") || param.equalsIgnoreCase("female")) {
            employee.setSex(param);
        } else if (param.startsWith("name:")) {
            employee.setName(param.split("\\s*:\\s*")[1].trim());
        } else if (EmailValidator.getInstance().isValid(param)) {
            employee.setEmail(param);
        } else if (DateValidator.getInstance().isValid(param)) {
            employee.setBirthDate(new java.sql.Date(DateValidator.getInstance().validate(param).getTime()).toLocalDate());
        } else if (param.startsWith("+")) {
            employee.setPhoneNumber(param);
        } else if (param.startsWith("id:")) {
            employee.setEmployeeID(Integer.valueOf(param.split("\\s*:\\s*")[1].trim()));
        } else if (BigDecimalValidator.getInstance().isValid(param)) {
            employee.setSalary(new BigDecimal(param));
        } else if (param.startsWith("dep:")) {
            company.getOrCreateDepartment(param.split("\\s*:\\s*")[1].trim()).addEmployee(employee);
        } else throw new IllegalArgumentException("Не могу распознать тип данных");
    }
}
