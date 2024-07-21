package com.reportgenerator.reportgenerator.entrypoint;

import com.reportgenerator.reportgenerator.response.ResponseComposer;
import com.reportgenerator.reportgenerator.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportEntryPointTest {
    @Mock
    private ReportService reportService;

    @Mock
    private ResponseComposer responseComposer;

    @InjectMocks
    private ReportEntryPoint reportEntryPoint;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateReportSuccess() {
        Path mockPath = mock(Path.class);
        when(mockPath.getFileName()).thenReturn(Path.of("test.csv"));
        when(mockPath.toUri()).thenReturn(Path.of("/test/path/test.csv").toUri());

        when(reportService.getReportDataAndExport()).thenReturn(mockPath);

        String expectedResponse = "{\"message\":\"The file has been downloaded to this path : /test/path/test.csv\"}";
        try (MockedStatic<ResponseComposer> mockedStatic = mockStatic(ResponseComposer.class)) {
            mockedStatic.when(() -> ResponseComposer.createJsonStringFromResponse(anyList()))
                    .thenReturn(expectedResponse);

            ResponseEntity<String> response = reportEntryPoint.generateReport();

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(MediaType.parseMediaType("text/csv"), response.getHeaders().getContentType());
            assertEquals("attachment; filename=test.csv", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
            assertEquals(expectedResponse, response.getBody());

            verify(reportService, times(1)).getReportDataAndExport();
        }
    }

    @Test
    void testGenerateReportWithNullPath() {
        when(reportService.getReportDataAndExport()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> reportEntryPoint.generateReport());

        verify(reportService, times(1)).getReportDataAndExport();
    }

    @Test
    void testGenerateReportWithException() {
        when(reportService.getReportDataAndExport()).thenThrow(new RuntimeException("Test exception"));

        assertThrows(RuntimeException.class, () -> reportEntryPoint.generateReport());

        verify(reportService, times(1)).getReportDataAndExport();
    }

    @Test
    void testGenerateReportWithEmptyFileName() {
        Path mockPath = mock(Path.class);
        when(mockPath.getFileName()).thenReturn(Path.of(""));
        when(mockPath.toUri()).thenReturn(Path.of("/test/path/").toUri());
        when(reportService.getReportDataAndExport()).thenReturn(mockPath);

        String expectedResponse = "{\"message\":\"The file has been downloaded to this path : /test/path/\"}";
        try (MockedStatic<ResponseComposer> mockedStatic = mockStatic(ResponseComposer.class)) {
            mockedStatic.when(() -> ResponseComposer.createJsonStringFromResponse(anyList()))
                    .thenReturn(expectedResponse);
            ResponseEntity<String> response = reportEntryPoint.generateReport();

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(MediaType.parseMediaType("text/csv"), response.getHeaders().getContentType());
            assertEquals("attachment; filename=", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
            assertEquals(expectedResponse, response.getBody());
        }
    }

    @Test
    void testGenerateReportWithLongFileName() {
        String longFileName = "a".repeat(300) + ".csv";
        Path mockPath = mock(Path.class);
        when(mockPath.getFileName()).thenReturn(Path.of(longFileName));
        when(mockPath.toUri()).thenReturn(Path.of("/test/path/" + longFileName).toUri());
        when(reportService.getReportDataAndExport()).thenReturn(mockPath);

        String expectedResponse = "{\"message\":\"The file has been downloaded to this path : /test/path/" + longFileName + "\"}";
        try (MockedStatic<ResponseComposer> mockedStatic = mockStatic(ResponseComposer.class)) {
            mockedStatic.when(() -> ResponseComposer.createJsonStringFromResponse(anyList()))
                    .thenReturn(expectedResponse);

            ResponseEntity<String> response = reportEntryPoint.generateReport();

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(MediaType.parseMediaType("text/csv"), response.getHeaders().getContentType());
            assertEquals("attachment; filename=" + longFileName, response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
            assertEquals(expectedResponse, response.getBody());
        }
    }
}
