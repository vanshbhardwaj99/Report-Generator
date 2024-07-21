package com.reportgenerator.reportgenerator.service;

import org.apache.commons.csv.CSVPrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.Logger;
import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ExportCSVServiceImplTest {

    @InjectMocks
    private ExportCSVServiceImpl exportCSVService;

    @Mock
    private Logger logger;

    @Mock
    private CSVPrinter csvPrinter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUniqueFileName() {
        String fileName = "test.csv";
        Path filePath = Paths.get(System.getProperty("user.home"), "Downloads").resolve(fileName);

        String uniqueFileName = exportCSVService.getUniqueFileName(filePath, fileName);
        assertEquals(fileName, uniqueFileName);
    }
}
