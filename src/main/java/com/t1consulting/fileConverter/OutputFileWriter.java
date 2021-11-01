package com.t1consulting.fileConverter;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

public class OutputFileWriter {
    final Logger log = Logger.getLogger("fileWriter");

    public void csvWriterAll(List<String[]> stringArray, Path path) throws Exception {
        CSVWriter writer = new CSVWriter(new FileWriter(path.toString()));
        writer.writeAll(stringArray);
        writer.close();
    }
}
