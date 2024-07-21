package com.reportgenerator.reportgenerator.config;

import com.reportgenerator.reportgenerator.exception.GenericException;
import com.reportgenerator.reportgenerator.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerConfigTest {
    @Mock
    private ReportService reportService;

    @InjectMocks
    private SchedulerConfig schedulerConfig;

    @Test
    void testScheduleReportGenerationSuccess() {
        Path mockPath = mock(Path.class);
        when(reportService.getReportDataAndExport()).thenReturn(mockPath);

        assertDoesNotThrow(() -> schedulerConfig.scheduleReportGeneration());

        verify(reportService, times(1)).getReportDataAndExport();
    }

    @Test
    void testScheduleReportGenerationThrowsException() {
        when(reportService.getReportDataAndExport()).thenThrow(new RuntimeException("Test exception"));

        GenericException exception = assertThrows(GenericException.class,
                () -> schedulerConfig.scheduleReportGeneration());

        assertTrue(exception.getCause() instanceof RuntimeException);
        assertEquals("Test exception", exception.getCause().getMessage());
    }

    @Test
    void testScheduleReportGenerationWithNullPath() {
        when(reportService.getReportDataAndExport()).thenReturn(null);

        assertDoesNotThrow(() -> schedulerConfig.scheduleReportGeneration());

        verify(reportService, times(1)).getReportDataAndExport();
    }


    @Test
    void testScheduleReportGenerationWithGenericException() {
        when(reportService.getReportDataAndExport()).thenThrow(new GenericException(new Exception("Test")));

        GenericException exception = assertThrows(GenericException.class,
                () -> schedulerConfig.scheduleReportGeneration());

        assertTrue(exception.getCause() instanceof GenericException);
        assertEquals("java.lang.Exception: Test", exception.getCause().getMessage());
    }
}
