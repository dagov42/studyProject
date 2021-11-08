package com.t1consulting;

import com.t1consulting.fileConverter.InputFileReader;
import com.t1consulting.fileConverter.OutputFileWriter;
import com.t1consulting.salaryCounter.entities.Company;

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
