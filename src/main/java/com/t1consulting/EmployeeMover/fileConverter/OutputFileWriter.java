package com.t1consulting.EmployeeMover.fileConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

public class OutputFileWriter {
    final Logger log = Logger.getLogger("fileWriter");

    public void csvWriter(List<String> stringArray, String outputFileName) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(outputFileName))) {
            for (String s : stringArray) {
                printWriter.println(s);
            }
        } catch (IOException e) {
            log.warning(e.getLocalizedMessage());
        }
    }

}
