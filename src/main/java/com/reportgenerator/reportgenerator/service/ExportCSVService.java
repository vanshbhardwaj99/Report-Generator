package com.reportgenerator.reportgenerator.service;

import com.reportgenerator.reportgenerator.model.OutputReport;

import java.nio.file.Path;
import java.util.List;

public interface ExportCSVService {

    public void exportToCSV(List<OutputReport> reports, Path filePath);

    public String getUniqueFileName(Path folder, String fileName);

}
