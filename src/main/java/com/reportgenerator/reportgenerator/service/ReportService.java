package com.reportgenerator.reportgenerator.service;

import com.reportgenerator.reportgenerator.entity.InputData;
import com.reportgenerator.reportgenerator.entity.ReferenceData;
import com.reportgenerator.reportgenerator.model.OutputReport;

import java.nio.file.Path;
import java.util.List;

public interface ReportService {

    public List<OutputReport> getReportData();

    public Path getReportDataAndExport();

    public List<InputData> getAllDistinctInputs();

    public List<ReferenceData> getAllReferences(String fileId, String refKey1, String refKey2);

}
