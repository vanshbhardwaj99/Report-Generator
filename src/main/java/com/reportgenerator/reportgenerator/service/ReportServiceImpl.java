package com.reportgenerator.reportgenerator.service;

import com.reportgenerator.reportgenerator.entity.InputData;
import com.reportgenerator.reportgenerator.entity.ReferenceData;
import com.reportgenerator.reportgenerator.model.OutputReport;
import com.reportgenerator.reportgenerator.repository.InputDataRepository;
import com.reportgenerator.reportgenerator.repository.ReferenceDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService{

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private static String CSV_FILE_NAME = "output_reports.csv";

    @Autowired
    private ExportCSVService exportCSVService;

    @Autowired
    private InputDataRepository inputDataRepository;

    @Autowired
    private ReferenceDataRepository referenceDataRepository;


    @Override
    public Path getReportDataAndExport() {
        List<OutputReport> reports = getReportData();

        logger.info(" report data : {}", reports);

        Path downloadsFolder = Paths.get(System.getProperty("user.home"), "Downloads");
        String fileName = exportCSVService.getUniqueFileName(downloadsFolder, CSV_FILE_NAME);
        Path filePath = downloadsFolder.resolve(fileName);
        exportCSVService.exportToCSV(reports, filePath);

        return filePath;
    }

    @Override
    public List<OutputReport> getReportData() {
        List<OutputReport> report = new ArrayList<>();
        List<InputData> inputData = getAllDistinctInputs();

        logger.info("all inputs : {}", inputData);

        inputData.forEach(input -> {
            List<ReferenceData> references = getAllReferences(input.getFileId(), input.getRefkey1(), input.getRefkey2());
            logger.info("references : {}", references);

            if(!references.isEmpty()){
                OutputReport opReport = OutputReport.of(input, references);
                logger.info("output report : {}", opReport);
                report.add(opReport);
            }
        });

        return report;
    }

    @Override
    public List<InputData> getAllDistinctInputs() {
        return inputDataRepository.findAllFileIdDistinctBy();
    }

    @Override
    public List<ReferenceData> getAllReferences(String fileId, String refKey1, String refKey2) {
        return referenceDataRepository.findAllByInputFileIdAndRefkey1OrRefkey2(fileId, refKey1, refKey2);
    }
}
