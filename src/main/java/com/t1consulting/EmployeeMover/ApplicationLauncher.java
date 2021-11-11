package com.t1consulting.EmployeeMover;

import com.t1consulting.EmployeeMover.fileConverter.InputFileReader;
import com.t1consulting.EmployeeMover.fileConverter.OutputFileWriter;
import com.t1consulting.EmployeeMover.entities.Company;

public class ApplicationLauncher {

    private static final String INPUT_FILE_NAME = "src/main/resources/employees.csv";
    private static final String OUTPUT_FILE_NAME = "src/main/resources/moveEmployees.csv";

    public static void main(String[] args) {

        InputFileReader reader = new InputFileReader(INPUT_FILE_NAME);
        Company t1Consulting = new Company("T1 consulting");
        reader.parseFileToEmployeeListAndAddToCompany(t1Consulting);

        OutputFileWriter writer = new OutputFileWriter();
        writer.csvWriter(t1Consulting.getPossibleToTransferEmployees(), OUTPUT_FILE_NAME);
    }
}
