package com.reportgenerator.reportgenerator.service;

import com.reportgenerator.reportgenerator.exception.GenericException;
import com.reportgenerator.reportgenerator.model.OutputReport;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ExportCSVServiceImpl implements ExportCSVService{

    private static final Logger logger = LoggerFactory.getLogger(ExportCSVServiceImpl.class);

    List<String> csvHeaders = List.of("batchId", "outfield1", "outfield2", "outfield3", "outfield4", "outfield5");

    @Override
    public void exportToCSV(List<OutputReport> reports, Path filePath) {

        try (Writer writer = Files.newBufferedWriter(filePath);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(csvHeaders.toArray(new String[0])))) {

            reports.forEach(report ->
            {
                try {
                    csvPrinter.printRecord(report.getBatchId(), report.getOutfield1(), report.getOutfield2(),
                            report.getOutfield3(), report.getOutfield4(), report.getOutfield5());
                } catch (IOException e) {
                    logger.error("Error while exporting csv : {}", e.getMessage());
                    throw GenericException.genericException(e);
                }
            });

            csvPrinter.flush();
        } catch (IOException e) {
            logger.error("IO Exception while exporting csv : {}", e.getMessage());
            throw GenericException.genericException(e);
        }
    }

    @Override
    public String getUniqueFileName(Path folder, String fileName) {
        Path filePath = folder.resolve(fileName);
        int counter = 1;

        while (Files.exists(filePath)) {
            String baseName = fileName.substring(0, fileName.lastIndexOf("."));
            String extension = fileName.substring(fileName.lastIndexOf("."));
            fileName = baseName + "_" + counter + extension;
            filePath = folder.resolve(fileName);
            counter++;
        }
        logger.info("fileName : {}", fileName);
        return fileName;
    }
}
