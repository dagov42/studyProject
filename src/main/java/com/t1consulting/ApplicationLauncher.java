package com.t1consulting;

import com.t1consulting.fileConverter.InputFileReader;
import com.t1consulting.fileConverter.OutputFileWriter;
import com.t1consulting.salaryCounter.entities.Company;
import com.t1consulting.salaryCounter.entities.Employee;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ApplicationLauncher {

    public static void main(String[] args) throws IOException {
        String inputFileName = "src/main/resources/employees.csv";
        String outputFileName = "src/main/resources/moveEmployees.csv";

        InputFileReader reader = new InputFileReader(inputFileName);
        try {
            Company t1Consulting = new Company("T1 consulting");
            List<Employee> employees = reader.getEmployeeList();
            t1Consulting.addEmployees(employees, t1Consulting);
            OutputFileWriter outputFileWriter = new OutputFileWriter();
            outputFileWriter.csvWriterAll(t1Consulting.getPossibleToTransferEmployees(), Path.of(outputFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
