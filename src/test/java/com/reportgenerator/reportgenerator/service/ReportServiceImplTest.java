package com.reportgenerator.reportgenerator.service;

import com.reportgenerator.reportgenerator.entity.InputData;
import com.reportgenerator.reportgenerator.entity.ReferenceData;
import com.reportgenerator.reportgenerator.model.OutputReport;
import com.reportgenerator.reportgenerator.repository.InputDataRepository;
import com.reportgenerator.reportgenerator.repository.ReferenceDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportServiceImplTest {

    @Mock
    private ExportCSVService exportCSVService;

    @Mock
    private InputDataRepository inputDataRepository;

    @Mock
    private ReferenceDataRepository referenceDataRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetReportDataAndExport_Success() {
        List<OutputReport> reports = List.of();
        Path filePath = Paths.get(System.getProperty("user.home"), "Downloads").resolve("test.csv");

        when(exportCSVService.getUniqueFileName(any(), any())).thenReturn("test.csv");
        doNothing().when(exportCSVService).exportToCSV(reports, filePath);

        Path result = reportService.getReportDataAndExport();

        assertEquals(filePath, result);
    }

    @Test
    public void testGetReportData_Success() {
        List<InputData> inputDataList = List.of(new InputData());

        when(inputDataRepository.findAllFileIdDistinctBy()).thenReturn(inputDataList);
        when(referenceDataRepository.findAllByInputFileIdAndRefkey1OrRefkey2(any(), any(), any())).thenReturn(new ArrayList<>());

        List<OutputReport> result = reportService.getReportData();

        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllDistinctInputs_Success() {
        List<InputData> inputDataList = List.of(new InputData());

        when(inputDataRepository.findAllFileIdDistinctBy()).thenReturn(inputDataList);

        List<InputData> result = reportService.getAllDistinctInputs();

        assertEquals(inputDataList, result);
    }

    @Test
    public void testGetAllReferences_Success() {
        List<ReferenceData> referenceDataList = List.of(new ReferenceData());

        when(referenceDataRepository.findAllByInputFileIdAndRefkey1OrRefkey2(any(), any(), any())).thenReturn(referenceDataList);

        List<ReferenceData> result = reportService.getAllReferences("fileId1", "refkey1", "refkey2");

        assertEquals(referenceDataList, result);
    }
}
